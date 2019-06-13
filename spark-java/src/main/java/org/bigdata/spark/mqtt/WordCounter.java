package org.bigdata.spark.mqtt;

import java.util.concurrent.TimeUnit;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

public class WordCounter {
	static InfluxDB influxDB;
	static BatchPoints batchPoints;
	
	public static void main(String[] args) throws Exception {
		String brokerUrl = "tcp://192.168.137.129:1883";
        String topic = "PAHO-TOPIC";
        
        influxDB = InfluxDBFactory.connect("http://192.168.137.129:8086", "admin", "admin");
        String dbNm = "test";
        influxDB.createDatabase(dbNm);
        
		influxDB.setDatabase(dbNm);
		
		batchPoints = BatchPoints
				.database(dbNm)
				.tag("async", "true")
				//.retentionPolicy(rpName)
				.consistency(ConsistencyLevel.ALL)
				.build();
        
        SparkConf sparkConf = new SparkConf().setAppName("JavaMQTTStreamWordCount");

        // check Spark configuration for master URL, set it to local if not configured
        if (!sparkConf.contains("spark.master")) {
            sparkConf.setMaster("local[*]");
        }

        SparkSession spark = SparkSession.builder()
                .config(sparkConf)
                .getOrCreate();

        // Create DataFrame representing the stream of input lines from connection to mqtt server
        Dataset<String> lines = spark
                .readStream()
                .format("org.apache.bahir.sql.streaming.mqtt.MQTTStreamSourceProvider")
                .option("topic", topic)
                .load(brokerUrl).select("value").as(Encoders.STRING());
        
        // Split the lines into words
        /*Dataset<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String x) {
                return Arrays.asList(x.split("__")).iterator();
            }
        }, Encoders.STRING());*/

        // Generate running word count
        Dataset<Row> wordCounts = lines.groupBy("value").count();

        // Start running the query that prints the running counts to the console
        StreamingQuery query = wordCounts.writeStream()
                .outputMode("complete")
                //.format("console")
                .foreach(new ForeachWriter<Row>() {
                    @Override
                    public boolean open(long partitionId, long version) {
                      //System.out.println("partitionId:" + partitionId + "version:" + version);
                      return true;
                    }

                    @Override
                    public void process(Row value) {
                      System.out.println("value : " + value.mkString());
                      insertData(value.mkString());
                    }

                    @Override
                    public void close(Throwable errorOrNull) {
                      //System.out.println("close");
                    }
                  })
                .start();

        query.awaitTermination();
        
        influxDB.deleteDatabase(dbNm);
        influxDB.close();
	}
	
	public static void insertData(String data) {
		Point point1 = Point.measurement("mqtt")
					.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
					.addField("carData", data)
					.build();
          
          batchPoints.point(point1);
          influxDB.write(batchPoints);
	}
}
