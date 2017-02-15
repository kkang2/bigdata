package org.bigdata.scala.spark

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object SimpleMain extends App {
	val conf:SparkConf = new SparkConf().setMaster("local").setAppName("SimpleApp")
	val sc = new JavaSparkContext(conf)
}