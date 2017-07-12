package org.bigdata.spark.common;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkContextUtil {
	public static JavaSparkContext getContext(SparkConf conf) {
		return new JavaSparkContext(conf);
	}
	
	public static void main(String[] args) {

	}

}
