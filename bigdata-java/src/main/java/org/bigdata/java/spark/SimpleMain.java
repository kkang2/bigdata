package org.bigdata.java.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SimpleMain {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("SimpleApp");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> contents = sc.textFile("src/main/resources/text1.txt");
		
		System.out.println(contents.count());
	}
}