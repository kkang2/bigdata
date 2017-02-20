package com.bigdata.akka.cluster.router

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.routing.RoundRobinGroup
import akka.cluster.routing.ClusterRouterGroupSettings
import akka.cluster.routing.ClusterRouterGroup
import akka.actor.Props
import com.bigdata.akka.cluster.actor.ClusterReceiverActor

object ClusterRouterGroupMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val clusterReceiverActor	= system.actorOf(Props[ClusterReceiverActor], "clusterReceiverActor") // create Actor
	
	val receiverRouter = system.actorOf(
  		ClusterRouterGroup(RoundRobinGroup(List("/user/clusterReceiverActor")), ClusterRouterGroupSettings (
    	totalInstances = 100, routeesPaths = List("/user/clusterReceiverActor"),
    	allowLocalRoutees = true, useRole = None)).props(),
  		name = "receiverRouter")
  		
  		Thread.sleep(3000)
  		
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
}