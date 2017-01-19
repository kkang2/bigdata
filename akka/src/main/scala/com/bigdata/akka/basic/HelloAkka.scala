package com.bigdata.akka.basic

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.actorRef2Scala
import akka.actor.Props

case class Greeting(message: String)

class Greeter extends Actor {
  def receive = {
    case Greeting(message) => println(s"[$message] received!")
  }
}

object HelloAkka extends App {
  val system = ActorSystem("GreetingActorSystem") // create ActorSystem

  val greeter = system.actorOf(Props[Greeter], "greeter") // create Actor
  
  greeter ! Greeting("Hello Akka!") // send Message
}