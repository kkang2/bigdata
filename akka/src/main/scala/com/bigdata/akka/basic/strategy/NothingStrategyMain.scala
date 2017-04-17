package com.bigdata.akka.basic.strategy

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.basic.actor.ExceptionActor

object NothingStrategyMain extends App {
	val system 					= ActorSystem("NothingStrategy") // create ActorSystem
  	val exceptionActor	= system.actorOf(Props[ExceptionActor], "exceptionActor") // create Actor
  	
  	exceptionActor ! "ArithmeticException"
  	/*exceptionActor ! "ActorKilledException"
  	exceptionActor ! "ㅇㅇ"*/
}