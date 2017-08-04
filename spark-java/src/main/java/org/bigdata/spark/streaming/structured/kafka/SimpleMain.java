package org.bigdata.spark.streaming.structured.kafka;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.Trigger;
import org.bigdata.spark.common.SparkConfUtil;
import org.bigdata.spark.common.SparkSessionUtil;

public class SimpleMain {

	public static void main(String[] args) throws Exception {
		SparkSession session = SparkSessionUtil.getSession(SparkConfUtil.getConf("SimpleApp", "local"));
		
		/* 옵션관련 페이지
		 * https://spark.apache.org/docs/2.1.0/structured-streaming-kafka-integration.html
		 * */
		Dataset<Row> kafkaSource = 
				session
					.readStream()
					.format("kafka")
					.option("subscribepattern", "sample*")
					.option("kafka.bootstrap.servers", "192.168.137.128:9092")
					.option("startingoffsets", "earliest")
					.load();
		
		StreamingQuery query = kafkaSource.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").writeStream()
		.format("console").trigger(Trigger.ProcessingTime("10 seconds")).queryName("from-kafka-to-console").
		outputMode(OutputMode.Append()).start();
		
		query.awaitTermination();
	}
}
