package com.bigdata.akka.cluster.router

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.routing.RoundRobinGroup
import akka.cluster.routing.ClusterRouterGroupSettings
import akka.cluster.routing.ClusterRouterGroup
import akka.actor.Props
import com.bigdata.akka.cluster.actor.ClusterReceiverActor

object ClusterRouterGroupMain1 extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster1.conf")) // create ActorSystem
  	
	val clusterReceiverActor	= system.actorOf(Props[ClusterReceiverActor], "clusterReceiverActor") // create Actor
}