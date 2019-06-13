package org.bigdata.spark.mqtt.influxdb;

import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

public class BasicUsages {

	public static void main(String[] args) {
		InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.137.129:8086", "admin", "admin");
		String dbName = "test";
		
		influxDB.setDatabase(dbName);
		//influxDB.createDatabase(dbName);
		
		//String rpName = "aRetentionPolicy";
		//influxDB.createRetentionPolicy(rpName, dbName, "30d", "30m", 2, true);

		BatchPoints batchPoints = BatchPoints
						.database(dbName)
						.tag("async", "true")
						//.retentionPolicy(rpName)
						.consistency(ConsistencyLevel.ALL)
						.build();
		
		Point point1 = Point.measurement("cpu")
							.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
							.addField("idle", 90L)
							.addField("user", 9L)
							.addField("system", 1L)
							.build();
		
		/*Point point2 = Point.measurement("disk")
							.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
							.addField("used", 80L)
							.addField("free", 1L)
							.build();*/
		
		batchPoints.point(point1);
		//batchPoints.point(point2);
		influxDB.write(batchPoints);
		
		Query query = new Query("SELECT idle FROM cpu", dbName);
		
		System.out.println(influxDB.query(query));
		//influxDB.dropRetentionPolicy(rpName, dbName);
		//influxDB.deleteDatabase(dbName);
	}
}
