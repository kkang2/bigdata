package org.bigdata.scala.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SimpleMain extends App {
	val conf = new SparkConf().setMaster("local").setAppName("SimpleApp")
	
	/* SparkConf 의 set 메소드로 컨피그 설정가능함.
		conf.set("spark.app.name", "SimpleApp");
		conf.set("spark.master", "local");*/
	
	val sc = new SparkContext(conf)
	
	val contents1 = sc.textFile("src/main/resources/text1.txt")
		
	println(contents1.count());
	
	val contents2 = sc.wholeTextFiles("src/main/resources")
	
	println(contents2);
	
	//contents2.saveAsTextFile("src/main/resources/text") // text 폴더에 파일들 생김
	
	val contents3 = contents1.filter(line => line.contains("Remoting"))
	
	contents3.foreach { x => println(x) }
	
	val numbersRdd = sc.parallelize(List(2, 5, 3, 1))
	
	println(numbersRdd.mean())
	
}