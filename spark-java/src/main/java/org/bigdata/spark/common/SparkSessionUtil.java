package org.bigdata.spark.common;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class SparkSessionUtil {
	public static SparkSession getSession(SparkConf conf) {
		return SparkSession.builder().config(conf).getOrCreate();
	}
	
	public static void main(String[] args) {

	}

}
