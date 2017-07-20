package org.bigdata.spark.exam.streaming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.KafkaUtils;

import kafka.serializer.StringDecoder;

/* kafka 연결 라이브러리 버전이 낮음. 최신버전으로 하면 코드가 바뀌어야 함
<dependency>
<groupId>org.apache.spark</groupId>
<artifactId>spark-streaming-kafka-0-8_2.11</artifactId>
<version>${spark.version}</version>
</dependency>*/
public class KafkaSample {

  public static void main(String[] args) throws Exception {

    SparkConf conf = new SparkConf()
            .setMaster("local[*]")
            .setAppName("KafkaSample");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.seconds(3));

    Map<String, Integer> topics1 = new HashMap<>();
    topics1.put("test", 3);

    Map<String, String> params = new HashMap<>();
    params.put("metadata.broker.list", "localhost:9092");

    Set<String> topics2 = new HashSet<>();
    topics2.add("test");

    JavaPairReceiverInputDStream<String, String> ds1 = KafkaUtils.createStream(ssc, "localhost:2181", "test-consumer-group1", topics1);
    JavaPairInputDStream<String, String> ds2 = KafkaUtils.<String, String, StringDecoder, StringDecoder> createDirectStream(ssc, 
        String.class, 
        String.class, 
        StringDecoder.class, 
        StringDecoder.class, 
        params, 
        topics2);

    ds1.print();
    ds2.print();

    ssc.start();
    ssc.awaitTermination();
  }
}