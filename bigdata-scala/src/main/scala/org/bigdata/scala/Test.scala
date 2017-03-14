package org.bigdata.scala

object Test extends App {
	val xs = List(2, 4, 6, 8, 10)
	
	val sum = xs.reduce((x,y) => x + y)
	
	println(sum)
	
	
	/*val line = "Scala is fun"
	val singleSpace = " "
	val words = line.split(singleSpace)
	
	words.foreach { x => println(x) }
	
	val arrayOfChars = words flatMap ( _.toList )
	
	println(arrayOfChars.size)
	
	arrayOfChars.foreach { x => println(x) }*/
}