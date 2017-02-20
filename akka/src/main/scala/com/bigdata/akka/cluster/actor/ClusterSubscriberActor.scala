package com.bigdata.akka.cluster.actor

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.cluster.ClusterEvent._
import akka.cluster.Cluster

class ClusterSubscriberActor extends Actor with ActorLogging {
	val cluster = Cluster(context.system)
 
  	def receive = {
  		case CurrentClusterState(members, unreachableMembers, seenBy, leader, roleLeaderMap) =>
  			log.info("CurrentClusterState members : {}, unreachableMembers : {}, seenBy : {}", members, unreachableMembers, seenBy)
  			log.info("CurrentClusterState leader : {}, roleLeaderMap : {}", leader, roleLeaderMap)
   	case MemberJoined(member) =>
      	log.info("Member status changed to Joining: {}", member.address)
   	case MemberUp(member) =>
      	log.info("Member is Up: {}", member.address)
   	case MemberWeaklyUp(member) =>
      	log.info("MemberWeaklyUp: {}", member.address)
      case LeaderChanged(leader) =>
      	log.info("Leader Changed: {}", leader)
      case RoleLeaderChanged(role, leader) =>
      	log.info("RoleLeaderChanged role : {}, leader : {}", role, leader)
      	
    	case MemberLeft(member) =>
      	log.info("MemberLeft: {}", member)
    	case MemberExited(member) =>
      	log.info("MemberExited: {}", member)
    	case UnreachableMember(member) =>
      	log.info("Member detected as unreachable: {}", member)
    	case MemberRemoved(member, previousStatus) =>
      	log.info("Member is Removed: {} previousStatus: {}", member.address, previousStatus)
    	case _ @ etc => println(s"etc event : $etc")
  	}
	
	// subscribe to cluster changes, re-subscribe when restart 
  	override def preStart(): Unit = {
   	cluster.subscribe(self, initialStateMode = InitialStateAsSnapshot, classOf[MemberEvent], classOf[UnreachableMember])
  	}
  	
  	override def postStop(): Unit = cluster.unsubscribe(self)
}