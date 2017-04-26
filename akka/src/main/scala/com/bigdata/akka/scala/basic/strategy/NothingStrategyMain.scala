package com.bigdata.akka.scala.basic.strategy

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.scala.basic.actor.ExceptionActor

object NothingStrategyMain extends App {
	val system 					= ActorSystem("NothingStrategy") // create ActorSystem
  	val exceptionActor	= system.actorOf(Props[ExceptionActor], "exceptionActor") // create Actor
  	
  	exceptionActor ! "ArithmeticException"
  	/*exceptionActor ! "ActorKilledException"
  	exceptionActor ! "ㅇㅇ"*/
}