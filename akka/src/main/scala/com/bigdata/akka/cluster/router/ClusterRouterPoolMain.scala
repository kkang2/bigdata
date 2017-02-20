package com.bigdata.akka.cluster.router

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.cluster.actor.ClusterSubscriberActor
import akka.cluster.routing.ClusterRouterPool
import akka.routing.RoundRobinPool
import akka.cluster.routing.ClusterRouterPoolSettings
import com.bigdata.akka.cluster.actor.ClusterReceiverActor

object ClusterRouterPoolMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val receiverRouter = system.actorOf(
		ClusterRouterPool(RoundRobinPool(5), ClusterRouterPoolSettings (
    	totalInstances = 100, maxInstancesPerNode = 5,
    	allowLocalRoutees = true/* false 로 설정시 routee 생성안됨 */, useRole = None)).props(Props[ClusterReceiverActor]),
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