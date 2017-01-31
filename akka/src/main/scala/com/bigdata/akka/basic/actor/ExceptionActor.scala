package com.bigdata.akka.basic.actor

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorInitializationException
import akka.actor.ActorKilledException

class ExceptionActor extends Actor with ActorLogging {
	def receive = {
  		case "ArithmeticException" 				=> throw new ArithmeticException("ArithmeticException")
  		case "NullPointerException" 				=> throw new NullPointerException("NullPointerException")
  		case "IllegalArgumentException" 	=> throw new IllegalArgumentException("IllegalArgumentException")
  		case "Exception" 									=> throw new Exception("Exception")
  		case "ActorKilledException" 				=> throw ActorKilledException("Exception")
  		case "Who are you?" 							=> log.info("it's me : {}", self)
  	}
	
	override def preStart() {
  		log.info("ExceptionActor preStart() execute!!!")
   }
	
	override def preRestart(reason: Throwable, message: Option[Any]) {
		log.info("ExceptionActor preRestart() execute!!! reason : [], message : []", reason, message)
	}
	
	override def postRestart(reason: Throwable) {
		log.info("ExceptionActor postRestart() execute!!! reason : []", reason)
	}
	
	override def postStop() {
		log.info("ExceptionActor postStop() execute!!!")
	}
}