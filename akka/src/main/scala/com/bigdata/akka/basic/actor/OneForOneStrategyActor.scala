package com.bigdata.akka.basic.actor

import scala.concurrent.duration.DurationInt

import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy.Escalate
import akka.actor.SupervisorStrategy.Restart
import akka.actor.SupervisorStrategy.Resume
import akka.actor.SupervisorStrategy.Stop
import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorKilledException
import akka.actor.Props
import akka.actor.ActorRef

class OneForOneStrategyActor extends Actor with ActorLogging {
	var exceptionActor:ActorRef = null
	
	/* 전략은 child actor에 적용됨. 자기 자신은 부모액터에 정의된 내용을 따름 */
	override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
   	case _: ArithmeticException      			=> Resume
    	case _: NullPointerException     			=> Restart
    	case _: IllegalArgumentException 		=> Stop
    	case _: Exception                				=> Escalate
  	}
	
	def receive = {
  		case "ArithmeticException" 				=> exceptionActor ! "ArithmeticException"
  		case "NullPointerException" 				=> exceptionActor ! "NullPointerException"
  		case "IllegalArgumentException" 	=> exceptionActor ! "IllegalArgumentException"
  		case "Exception" 									=> exceptionActor ! "Exception"
  		case _ @ msg 										=> println(s"_ @ msg => [$msg] received!")
  	}
	
	override def preStart() {
  		log.info("OneForOneStrategyActor preStart() execute!!!")
  		
  		exceptionActor = context.actorOf(Props[ExceptionActor], "exceptionActor") // create Actor
   }
	
	override def preRestart(reason: Throwable, message: Option[Any]) {
		log.info("OneForOneStrategyActor preRestart() execute!!! reason : [], message : []", reason, message)
	}
	
	override def postRestart(reason: Throwable) {
		log.info("OneForOneStrategyActor postRestart() execute!!! reason : []", reason)
	}
	
	override def postStop() {
		log.info("OneForOneStrategyActor postStop() execute!!!")
	}
}