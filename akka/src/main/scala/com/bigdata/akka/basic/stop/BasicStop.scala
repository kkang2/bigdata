package com.bigdata.akka.basic.stop

import com.bigdata.akka.basic.Greeting
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

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