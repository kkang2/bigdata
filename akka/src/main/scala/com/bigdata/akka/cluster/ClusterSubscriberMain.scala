package com.bigdata.akka.cluster

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.cluster.actor.SimpleClusterActor
import com.typesafe.config.ConfigFactory

object SimpleClusterMain extends App {
	val system 			= ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	val clusterActor = system.actorOf(Props[SimpleClusterActor], "simpleClusterActor") // create Actor
}