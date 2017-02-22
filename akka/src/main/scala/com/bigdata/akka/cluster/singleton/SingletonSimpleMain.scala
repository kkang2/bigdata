package com.bigdata.akka.cluster.singleton

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.cluster.singleton.ClusterSingletonManager
import akka.actor.PoisonPill
import akka.actor.Props
import com.bigdata.akka.cluster.actor.ClusterReceiverActor
import akka.cluster.singleton.ClusterSingletonManagerSettings

/*
 *		2개의 싱글턴 액터가 있을때 하나가 죽고 그 죽었던 액터가 다시 떠야 기존에 떠있던 액터가 Oldest가 된다.
 * */
object SingletonSimpleMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster1.conf")) // create ActorSystem
	
	system.actorOf(ClusterSingletonManager.props(
      singletonProps = Props(classOf[ClusterReceiverActor]),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)),
      name = "broker-manager")
}