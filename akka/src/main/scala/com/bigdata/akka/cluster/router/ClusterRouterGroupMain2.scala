package com.bigdata.akka.cluster.router

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.routing.RoundRobinGroup
import akka.cluster.routing.ClusterRouterGroupSettings
import akka.cluster.routing.ClusterRouterGroup
import akka.actor.Props
import com.bigdata.akka.cluster.actor.ClusterReceiverActor

object ClusterRouterGroupMain2 extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster2.conf")) // create ActorSystem
  	
	val clusterReceiverActor	= system.actorOf(Props[ClusterReceiverActor], "clusterReceiverActor") // create Actor
	
	Thread.sleep(5000)
	
	/* allowLocalRoutees = false 로 설정시 routee가 생성되지 않았을경우 메세지 전달 실패 */
	val receiverRouter = system.actorOf(
  		ClusterRouterGroup(RoundRobinGroup(List("/user/clusterReceiverActor")), ClusterRouterGroupSettings (
    	totalInstances = 100, routeesPaths = List("/user/clusterReceiverActor"),
    	allowLocalRoutees = true, useRole = None)).props(),
  		name = "receiverRouter")
  		
  		println("라우터를 이용한 메세지 전송!" + receiverRouter)
  		
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
  		receiverRouter ! "Hi!!"
}