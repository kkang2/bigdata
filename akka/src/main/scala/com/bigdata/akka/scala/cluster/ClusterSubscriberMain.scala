package com.bigdata.akka.scala.cluster

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Props
import com.bigdata.akka.scala.cluster.actor.ClusterSubscriberActor

object SimpleClusterMain extends App {
	val system 				= ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster1.conf")) // create ActorSystem
  	val clusterActor1	= system.actorOf(Props[ClusterSubscriberActor], "simpleClusterActor1") // create Actor
  	
  	Thread.sleep(3000)
  	
  	val clusterActor2 = system.actorOf(Props[ClusterSubscriberActor], "simpleClusterActor2") // create Actor
}