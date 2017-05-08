package com.bigdata.akka.java.basic.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Greeter extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	@Override
	  public Receive createReceive() {
	    return receiveBuilder()
	      .match(String.class, s -> {
	        log.info("Received String message: {}", s);
	      })
	      .matchAny(o -> log.info("received unknown message"))
	      .build();
	  }
}
