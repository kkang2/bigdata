package org.bigdata.java.kafka.producer;

import java.util.Date;
//import util.properties packages
import java.util.Properties;
import java.util.concurrent.Future;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;
//import simple producer packages
import org.apache.kafka.clients.producer.Producer;
//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducer {
	public static void main(String[] args) throws Exception {
		
	    //Assign topicName to string variable
	    String topicName = "sample";
	      
	    // create instance for properties to access producer configs   
	    Properties props = new Properties();
	      
	    //Assign localhost id
	    props.put("bootstrap.servers", "192.168.137.128:9092");
	      
	    //Set acknowledgements for producer requests.
	    props.put("acks", "all");
	      
	    //If the request fails, the producer can automatically retry,
	    props.put("retries", 0);
	      
	    //Specify buffer size in config
	    props.put("batch.size", 16384);
	      
	    //Reduce the no of requests less than 0   
	    props.put("linger.ms", 1);
	      
	    //The buffer.memory controls the total amount of memory available to the producer for buffering.   
	    props.put("buffer.memory", 33554432);
	      
	    props.put("key.serializer", 
	         "org.apache.kafka.common.serialization.StringSerializer");
	         
	    props.put("value.serializer", 
	         "org.apache.kafka.common.serialization.StringSerializer");
	      
	    Producer<String, String> producer = new KafkaProducer<String, String>(props);
	      
	    /* 콜백처리예(전송실패시 다시 큐에 넣는다.)
	       kafkaProducer.send(new ProducerRecord<String, String>(serviceId, message), new Callback() {
	    	    public void onCompletion(RecordMetadata metadata, Exception exception) {
	    	        if (exception != null) {
	    	            queue.enQueue(message);
	    	            errorLog.sendError("Send Error in Callback", exception);
	    	        }
	    	    }
	    	});*/
	      
	    boolean isGo = true;
	    int i = 100;
	    String value = "";
	      
	    while(isGo) {
	    	value = new Date().toString();
	
	    	System.out.println("메세지 전송 -> key :" + i + ", value : " + value);
	
	    	Future<RecordMetadata> meta = producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), value));
	    	
	    	i++;
	    	Thread.sleep(1000);
	    	System.out.println("메세지 전송 -> 완료 : " + meta.isDone());
	    }
	      
	    System.out.println("Message sent successfully");
	    producer.close();
	}
}

/*https://www.tutorialspoint.com/apache_kafka/apache_kafka_simple_producer_example.htm*/