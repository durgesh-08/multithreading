package com.durgesh;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerExample {
	
	private static AtomicInteger counter = new AtomicInteger(0);
	
	public static void main(String[] args) {
		AtomicIntegerExample atomicInteger = new AtomicIntegerExample();
		Thread thread1 = new Thread(atomicInteger::increment, "thread1");
		Thread thread2 = new Thread(atomicInteger::increment, "thread2");
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("counter: " + counter);
	}
	
	public void increment() {
		IntStream.range(0, 10000).forEach(i -> counter.getAndIncrement());
	}
}
