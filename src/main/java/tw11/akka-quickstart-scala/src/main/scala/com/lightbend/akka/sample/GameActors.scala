package com.lightbend.akka.sample

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GameActors extends App {

  object Game {
    def props(teamOneName: String, teamTwoName: String): Props = Props(new Game(teamOneName, teamTwoName))

    case object Play

    case object Stop

    case object PassToOpponent

    final case class Goal()

  }

  class Game(teamOneName: String, teamTwoName: String) extends Actor {

    import Game._
    import Referee.StartGame
    import Team._

    var score1 = 0
    var score2 = 0
    val referee: ActorRef = context.actorOf(Referee.props())
    val team2: ActorRef = context.actorOf(Team.props(teamTwoName, context.self), teamTwoName)
    teams += team2
    while (!canFirstPlayerInitialize) {
      Thread.sleep(100)
    }
    val team1: ActorRef = context.actorOf(Team.props(teamOneName, context.self))
    teams += team1


    def receive = {
      case Play =>
        referee ! StartGame(10000)
        team1 ! Start
      case Goal => {
        if (context.sender() == team1) {
          score1 = score1 + 1
        }
        else {
          score2 = score2 + 1
        }
        println("goal score: " + teamTwoName + " " + score1 + ":" + score2 + " " + teamOneName)
      }
      case Stop =>
        team1 ! StopTeam
        team2 ! StopTeam
    }
  }

  object Referee {
    def props(): Props = Props(new Referee())

    final case class StartGame(time: Int)

  }

  class Referee() extends Actor {

    import Game._
    import Referee._

    override def receive = {
      case StartGame(time: Int) =>
        sleepAndSendStop(time)
    }

    def sleepAndSendStop(time: Int): Future[Unit] = Future {
      Thread.sleep(time)
      context.parent ! Stop
    }
  }

  object Team {
    def props(teamName: String, parent: ActorRef): Props = Props(new Team(teamName, parent))

    case object Start

    case object StopTeam

    case object Shoot

  }

  class Team(teamName: String, parent: ActorRef) extends Actor {

    import Game.Goal
    import Player._
    import Team._

    val r = new scala.util.Random
    var stop = false
    val myPlayers = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    for (i <- 0 to 11) {
      players += context.actorOf(Player.props(i, teamName))
      myPlayers += context.actorOf(Player.props(i, teamName))
    }
    canFirstPlayerInitialize = true

    def receive = {
      case Start =>
        if (!stop) {
          println(teamName + " start game")
          myPlayers(r.nextInt(12)) ! Kick
        }
      case StopTeam =>
        stop = true
        println(teamName + " stop game")
        for (player <- myPlayers) {
          player ! StopPlayer
        }

      case Shoot =>
        if (!stop) {
          val rand = r.nextInt(10)
          if (rand < 7) {
            println(teamName + " reports goal")
            context.parent ! Goal
          }
          else {
            println(teamName + " reports miss")
          }
          myPlayers(r.nextInt(12)) ! Kick
        }
    }
  }

  object Player {
    def props(id: Int, team: String): Props = Props(new Player(id, team))

    case object Kick

    case object StopPlayer

  }

  class Player(id: Int, team: String) extends Actor {

    import Player._
    import Team._

    val r = new scala.util.Random
    var stop = false

    def receive = {
      case Kick =>
        Thread.sleep(1000)
        if (!stop) {
          val action = r.nextInt(2)
          if (action == 0) {
            println(team + " " + id + " kick")
            players(r.nextInt(24)) ! Kick
          }
          if (action == 1) {
            println(team + " " + id + " shoot")
            if (context.parent == teams(0)) {
              teams(1) ! Shoot
            }
            else {
              teams(0) ! Shoot
            }
          }
        }
      case StopPlayer =>
        stop = true
    }
  }

  var canFirstPlayerInitialize = false
  var players = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
  var teams = scala.collection.mutable.ArrayBuffer.empty[ActorRef]

  def startSimulation() {
    import Game._
    players = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    teams = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    val system: ActorSystem = ActorSystem("Game_Symulator")
    val game: ActorRef = system.actorOf(Game.props("Witcher", "I_hate_Rachel_Green_Club"))
    game ! Play
  }

}


object GameActorsStart extends App {
  var v = new GameActors()
  v.startSimulation()
}
