package com.lightbend.akka.sample

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

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
    import Team.Start

    var score1 = 0
    var score2 = 0
    val team1: ActorRef = context.actorOf(Team.props(teamOneName, context.self))
    val team2: ActorRef = context.actorOf(Team.props(teamTwoName, context.self), teamTwoName)
    teams += team1
    teams += team2


    def receive = {
      case Play =>
        team1 ! Start
      case Goal() => {
        print("GOOOOL")
        if (context.sender() == team1) {
          score1 = score1 + 1
        }
        else {
          score2 = score2 + 1
        }
        println("goal score: " + score1 + ":" + score2)
      }
      case Stop => team1 ! Stop
        team2 ! Stop
    }
  }

  object Team {
    def props(teamName: String, parent: ActorRef): Props = Props(new Team(teamName, parent))

    case object Start

    case object Stop

    case object Shoot

  }

  class Team(teamName: String, parent: ActorRef) extends Actor {

    import Game.Goal
    import Player._
    import Team._

    val r = new scala.util.Random
    val myPlayers = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    for (i <- 0 to 11) {
      players += context.actorOf(Player.props(i, teamName))
      myPlayers += context.actorOf(Player.props(i, teamName))
    }

    def receive = {
      case Start =>
        println(teamName + "start game")
        players(r.nextInt(24)) ! Kick
      case Stop =>
        println(teamName + "stop game")
        for (player <- myPlayers) {
          player ! Stop
        }
      case Shoot =>
        val rand = r.nextInt(10)
        if (rand < 7) {
          context.parent ! Goal
          println(teamName + " reports goal")
        }
        else {
          println(teamName + " reports miss")
        }
        players(r.nextInt(24)) ! Kick
    }
  }

  object Player {
    def props(id: Int, team: String): Props = Props(new Player(id, team))

    case object Kick

  }

  class Player(id: Int, team: String) extends Actor {

    import Player._
    import Team._

    val r = new scala.util.Random
    var stop = false

    def receive = {
      case Kick =>
        if (!stop) {
          Thread.sleep(1000)
          val action = r.nextInt(2)
          if (action == 0) {
            println(team + " " + id + " kick")
            players(r.nextInt(24)) ! Kick
          }
          if (action == 1) {
            println(team + " " + id + "shoot")
            teams(r.nextInt(2)) ! Shoot
          }
        }
      case
        Stop => stop = true
    }
  }

  var players = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
  var teams = scala.collection.mutable.ArrayBuffer.empty[ActorRef]

  def startSimulation() {
    import Game._
    players = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    teams = scala.collection.mutable.ArrayBuffer.empty[ActorRef]
    val system: ActorSystem = ActorSystem("Game_Symulator")
    val game: ActorRef = system.actorOf(Game.props("Witcher", "I_hate_Rachel_Green_Club"))
    game ! Play
    Thread.sleep(10000)
    game ! Stop
  }

}


object GameActorsStart extends App {
  var v = new GameActors()
  v.startSimulation()
}
