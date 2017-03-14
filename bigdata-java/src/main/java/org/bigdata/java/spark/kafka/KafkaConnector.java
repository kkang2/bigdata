package org.bigdata.java.spark.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Tuple2;

public class KafkaConnector {
	static final Logger logger = LoggerFactory.getLogger(KafkaConnector.class);
	
	public static void main(String[] args) throws Exception {
		SparkConf sparkConf = new SparkConf();
		
		sparkConf.setMaster("local[*]");
        sparkConf.setAppName("TEST-KafkaConnector");
		
		logger.info("SparkConf list ---- ");
		
        for(Tuple2<String, String> tuple : sparkConf.getAll()){
        	logger.info("{} : {}", tuple._1(), tuple._2());
        }
        
        JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, new Duration(10000));
        
        /* https://kafka.apache.org/documentation */
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "192.168.137.128:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "test");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        
        JavaInputDStream<ConsumerRecord<String, String>> stream = 
	    	KafkaUtils.createDirectStream(
	    		jsc,
	    		LocationStrategies.PreferConsistent(),
	    		ConsumerStrategies.<String, String>Subscribe(Arrays.asList("test"), kafkaParams)
	    	);
        
        stream.mapToPair(
		  new PairFunction<ConsumerRecord<String, String>, String, String>() {
		    @Override
		    public Tuple2<String, String> call(ConsumerRecord<String, String> record) {
		    	logger.info("key : {}, value : {}", record.key(), record.value());
		    	return new Tuple2<>(record.key(), record.value());
		    }
		  }).print();
        
        /*stream.foreachRDD(new VoidFunction<JavaRDD<ConsumerRecord<String, String>>>() {
        	  @Override
        	  public void call(JavaRDD<ConsumerRecord<String, String>> rdd) {
        	    OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
        	    
        	    logger.info("rdd.first() : " + rdd.rdd().toString());
        	    // begin your transaction

        	    // update results
        	    // update offsets where the end of existing offsets matches the beginning of this batch of offsets
        	    // assert that offsets were updated correctly

        	    // end your transaction
        	  }
        	});*/
        
        
        jsc.start();
        jsc.awaitTermination();

        registerShutdownHook();
	}
	
	public static void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("addShutdownHook");
		}));
	}
}
