package com.bigdata.akka.scala.basic.stop

import com.bigdata.akka.scala.basic.Greeting
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/*
 * 부모액터를 stop 하면 자식액터도 stop됨
 **/

class StopActorChild extends Actor {
	def receive = {
  		case "Hi" => println("Hi!!!!")
  	}
	
	override def postStop() {
		println("======== StopActorChild postStop invoke ========")
		println(self + " <== 종료됨")
	}
}

class StopActor extends Actor {
	def receive = {
		case "create"	=> context.actorOf(Props[StopActorChild], "stopActorChild") // create Child Actor
  		case "stop" 		=> context.stop(self)
  	}
	
	override def postStop() {
		println("======== StopActor postStop invoke ========")
		println(self + " <== 종료됨")
	}
}

object BasicStop extends App {
	val system 		= ActorSystem("StopActorSystem") // create ActorSystem
  	val stopActor 	= system.actorOf(Props[StopActor], "stopActor") // create Actor
  	
  	stopActor ! "create"
  	
  	Thread.sleep(3000)
  	
  	stopActor ! "stop"
}