package com.bigdata.akka.basic.router

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.basic.actor.ExceptionActor
import akka.actor.ActorRef
import akka.routing.RoundRobinPool
import akka.routing.FromConfig

object RoundRobinRouterMain extends App {
	val system 	= ActorSystem("RoundRobinActorSystem") // create ActorSystem
	
  	val roundRobinRouter: ActorRef 							= system.actorOf(RoundRobinPool(5).props(Props[ExceptionActor]), "roundRobinRouter")
  	val roundRobinRouterFromConfig: ActorRef 	= system.actorOf(FromConfig.props(Props[ExceptionActor]), "roundRobinRouterFromConfig")
  	
  	println(roundRobinRouter)
  	println(roundRobinRouterFromConfig)
  	
  	Thread.sleep(2000)
  	
  	roundRobinRouter ! "Who are you?"
  	roundRobinRouter ! "Who are you?"
  	roundRobinRouter ! "Who are you?"
  	roundRobinRouter ! "Who are you?"
  	roundRobinRouter ! "Who are you?"
  	
  	Thread.sleep(1000)
  	
  	println()
  	
  	roundRobinRouterFromConfig ! "Who are you?"
  	roundRobinRouterFromConfig ! "Who are you?"
  	roundRobinRouterFromConfig ! "Who are you?"
  	roundRobinRouterFromConfig ! "Who are you?"
  	roundRobinRouterFromConfig ! "Who are you?"
}