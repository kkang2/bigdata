package com.bigdata.akka.basic.strategy

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.basic.actor.ExceptionActor
import com.bigdata.akka.basic.actor.OneForOneStrategyActor

object OneForOneStrategyMain extends App {
	val system 									= ActorSystem("OneForOneStrategy") // create ActorSystem
  	val oneForOneStrategyActor	= system.actorOf(Props[OneForOneStrategyActor], "oneForOneStrategyActor") // create Actor
  	
  	oneForOneStrategyActor ! "ArithmeticException"
  	
  	//Thread.sleep(2000)
  	
  	//oneForOneStrategyActor ! "Who are you?"
  	//oneForOneStrategyActor ! "IllegalArgumentException"
  	//oneForOneStrategyActor ! "NullPointerException"
  	//oneForOneStrategyActor ! "Exception"
}