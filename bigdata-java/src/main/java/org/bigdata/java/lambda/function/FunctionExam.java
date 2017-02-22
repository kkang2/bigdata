package org.bigdata.java.lambda.function;

import java.util.function.Function;

/*
 * 추상메소드 : R apply(T t)
 * */
public class FunctionExam {
	public static void main(String[] args) {
		Function<String, Integer> toInteger = Integer::valueOf;
		
		System.out.println(toInteger.apply("44"));
		
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		System.out.println(backToString.apply("123"));
	}
}
