package com.bigdata.akka.scala.basic

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.actorRef2Scala
import akka.actor.Props
import java.util.Date
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.Await

case class Greeting(message: String)

class Greeter extends Actor {
	def receive = {
  		case msg @ Greeting("askMsg") =>
  			println(s"msg @ Greeting(askMsg) => [$msg] received! " + new Date())
  			Thread.sleep(3000)
  			sender() ! "ask msg return!"
    	case Greeting(message) 				=> println(s"Greeting(message) => [$message] received!")
    	
    	case num:Int if num == 4 			=> println(s"num:Int if num == 4 => [$num] received!")
    	case num:Int 									=> println(s"num:Int => [$num] received!")
    	
    	case etc @ _ 										=> println(s"etc @ _ => [$etc] received!")
  	}
}

object HelloAkkaMain extends App {
	implicit val timeout = Timeout(5 seconds)  
	val system = ActorSystem("GreetingActorSystem") // create ActorSystem
  	val greeter = system.actorOf(Props[Greeter], "greeter") // create Actor
  
  	greeter ! Greeting("! use Hello Akka!") // send Message
  	greeter.tell(Greeting("tell use Hello Akka!"), Actor.noSender) // send Message
  	greeter ! 3 // send Message
  	greeter ! 4 // send Message
  	greeter ! "text msg" // send Message
  	
  	
  	val future = greeter ? Greeting("askMsg")
  	val msg 	= Await.result(future, timeout.duration).asInstanceOf[String]
	
	println(s"future => [$msg] received! " + new Date())
}