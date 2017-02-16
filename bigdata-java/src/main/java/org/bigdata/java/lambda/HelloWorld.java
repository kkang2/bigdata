package org.bigdata.java.lambda;

public class HelloWorld {
	public static void main(String[] args) {
		new Thread(() -> {
			System.out.println("Hello World");
		}).start();
	}
}