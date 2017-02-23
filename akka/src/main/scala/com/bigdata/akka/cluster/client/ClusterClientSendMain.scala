package com.bigdata.akka.cluster.client

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import com.bigdata.akka.cluster.actor.client.ReceptionActor
import akka.cluster.client.ClusterClientReceptionist
import akka.cluster.client.ClusterClient
import akka.cluster.client.ClusterClientSettings
import akka.actor.ActorPath

object ClusterClientSendMain extends App {
	val system = ActorSystem("ClusterSystem", ConfigFactory.parseResources("cluster.conf")) // create ActorSystem
  	
	val receptionActor1	= system.actorOf(Props[ReceptionActor], "receptionActor1") // create Actor
	val receptionActor2	= system.actorOf(Props[ReceptionActor], "receptionActor2") // create Actor
  	
	ClusterClientReceptionist(system).registerService(receptionActor1)
	ClusterClientReceptionist(system).registerService(receptionActor2)
	
	val initialContacts = Set (
  		ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2551/system/receptionist"))
  
	val sendActor = system.actorOf(ClusterClient.props(
   	ClusterClientSettings(system).withInitialContacts(initialContacts)), "sendActor")
    
    
  	sendActor ! ClusterClient.Send("/user/receptionActor1", "hello", localAffinity = true)
  	sendActor ! ClusterClient.SendToAll("/user/receptionActor2", "hi")
}