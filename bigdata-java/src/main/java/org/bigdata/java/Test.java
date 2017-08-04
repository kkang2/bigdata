package org.bigdata.java;

import java.util.Arrays;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) {
		String[] stringArr = { "a", "b", "c", "d" };
		Stream<String> stream = Stream.of(stringArr);
		String[] arr = stream.toArray(size -> new String[size]);
		System.out.println(Arrays.toString(arr));
	}
}