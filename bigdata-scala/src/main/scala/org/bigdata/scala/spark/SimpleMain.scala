package org.bigdata.scala.spark

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object SimpleMain extends App {
	val conf:SparkConf = new SparkConf().setMaster("local").setAppName("SimpleApp")
	val sc = new JavaSparkContext(conf)
	
	val contents1 = sc.textFile("src/main/resources/text1.txt");
		
	println(contents1.count());
	
	val contents2 = sc.wholeTextFiles("src/main/resources")
	
	println(contents2);
	
	contents2.saveAsTextFile("src/main/resources/text") // text 폴더에 파일들 생김
}