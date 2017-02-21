package org.bigdata.java.lambda.function;

import org.bigdata.java.lambda.interfaces.Func;

public class CustomFunctionMain {

	public static void main(String[] args) {
		Func add 		= (int a, int b) -> a + b;
		Func add2		= (int a, int b) -> { return a + b; };
		Func subtract	= (int a, int b) -> a - b;
		
		System.out.println(add.calc(1, 2));
		System.out.println(add2.calc(1, 2));
		System.out.println(subtract.calc(1, 2));
	}
}
