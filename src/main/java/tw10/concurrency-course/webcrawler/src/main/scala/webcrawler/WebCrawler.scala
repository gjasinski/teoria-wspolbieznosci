package webcrawler

import java.io.File
import java.net.MalformedURLException

import org.htmlcleaner.{CleanerProperties, TagNode}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.Source

object WebCrawler extends App {

  import java.net.URL
  import org.htmlcleaner.HtmlCleaner
  val cleaner: HtmlCleaner = new HtmlCleaner
  val props: CleanerProperties = cleaner.getProperties

  val awaitMax: Duration = 1000 second

  def downloadAndPrint(url: URL, deep: Int): Future[Boolean] = Future {
    println(deep + " " + url)
    if (deep == 0) {
      true
    }
    else {
      try {
        extractElements(url)
          .map(element => convertToUrl(element.getAttributeByName("href")))
          .filter(optional => optional.isDefined)
          .map(optionalUrl => {
            savePage(optionalUrl.get)
            downloadAndPrint(optionalUrl.get, deep - 1)
          })
          .foreach(future => Await.result(future, awaitMax))
      }
      catch {
        case e: MalformedURLException => println("MALFORMEND URL")
        case x: Exception => print(x)
      }
      true
    }
  }

  def convertToUrl(url: String): Option[URL] = Option {
    try {
      new URL(url)
    }
    catch {
      case e: MalformedURLException => null
    }
  }

  def extractElements(url: URL): Array[TagNode] = {
    val rootNode: TagNode = cleaner.clean(url)
    rootNode.getElementsByName("a", true)
  }

  def savePage(url: URL): Future[Unit] = Future{
    import java.io.BufferedWriter
    import java.io.FileWriter
    import java.io.IOException
    val directoryName = "download/" + url.toString
      .replace("http://", "")
      .replace("https://", "")
      .replace(".", "/")
    val fileName = "index.html"

    val directory: File = new File(directoryName)
    if (!directory.exists) {
      directory.mkdirs
    }

    val file: File = new File(directoryName + "/" + fileName)
    try {
      val fw = new FileWriter(file.getAbsoluteFile)
      val bw = new BufferedWriter(fw)
      val html = Source.fromURL(url)
      bw.write(html.mkString)
      bw.close()
    } catch {
      case e: IOException => println("Dir/File creating ex" + e)
      case x: Exception => println(x)
    }
  }

  val googleUrl: URL = new URL("https://www.google.com/")
  val result: Future[Boolean] = downloadAndPrint(googleUrl, 2)
  Await.result(result, awaitMax)
}
