package org.bigdata.spark.common;

import org.apache.spark.SparkConf;

/*
 * SparkConf 의 set 메소드로 컨피그 설정가능함.'
 * conf.set("spark.app.name", "SimpleApp");
 * conf.set("spark.master", "local");
 **/
public class SparkConfUtil {
	/*
	 * Set a name for your application. Shown in the Spark web UI.
	 **/
	public static SparkConf getConf(String appNm) {
		return new SparkConf().setAppName(appNm);
	}
	
	/*
	 * The master URL to connect to, such as "local" to run locally with one thread, 
	 * "local[4]" to run locally with 4 cores, or "spark://master:7077" to run on a Spark standalone cluster.
	 **/
	public static SparkConf getConf(String appNm, String master) {
		return new SparkConf().setAppName(appNm).setMaster(master);
	}
	
	public static void main(String[] args) {

	}

}
