package com.bigdata.akka.scala.cluster.router

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.scala.cluster.actor.ClusterSubscriberActor
import akka.cluster.routing.ClusterRouterPool
import akka.routing.RoundRobinPool
import akka.cluster.routing.ClusterRouterPoolSettings
import com.bigdata.akka.scala.cluster.actor.ClusterReceiverActor

object ClusterRouterPoolMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val receiverRouter = system.actorOf(
		ClusterRouterPool(RoundRobinPool(5), ClusterRouterPoolSettings (
    	totalInstances = 100, maxInstancesPerNode = 5,
    	allowLocalRoutees = true/* false 로 설정시 routee 생성안되기 때문에 메세지 전달 실패 */, useRole = None)).props(Props[ClusterReceiverActor]),
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