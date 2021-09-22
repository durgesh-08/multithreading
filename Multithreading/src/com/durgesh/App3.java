package com.durgesh;
/*

class Process   {
	
	public void produce() throws InterruptedException   {
		
		synchronized (this) {
			System.out.println("Running the produce method");
			wait();
			System.out.println("Again in the producer method");
		}
	
	}
	
	public void consume() throws InterruptedException   {
		
		Thread.sleep(1000);
		synchronized (this) {
			System.out.println("Consume method is executed");
			notify();
			// it is not going to handle the lock: we can make other operations
			Thread.sleep(5000);
		}
	
	}
	
}
*/

import java.util.ArrayList;
import java.util.List;

class Processor {
	private List<Integer> list = new ArrayList<>();
	private static final int UPPER_LIMIT = 5;
	private static final int LOWER_LIMIT = 0;
	private int value = 0;
	
	private final Object lock = new Object();
	
	public void producer() throws InterruptedException {
	
		synchronized (lock) {
			while (true)    {
				if(list.size() == UPPER_LIMIT)  {
					System.out.println("Waiting for removing items...");
					lock.wait();
				}
				else    {
					System.out.println("Adding : " + value);
					list.add(value);
					value++;
					// we can call to notify - because the other thread will be notified
					// only when it is in a waiting state
					lock.notify();
				}
				Thread.sleep(500);
			}
		}
		
	}
	
	public void consumer() throws InterruptedException {
		synchronized (lock) {
			while (true)    {
				if(list.size() == LOWER_LIMIT)  {
					System.out.println("Waiting for adding items...");
					value = 0;
					lock.wait();
				}
				else    {
					System.out.println("Removing : " + list.remove(list.size() - 1));
					lock.notify();
				}
				Thread.sleep(500);
			}
		}
	}
}

public class App3 {
	public static void main(String[] args) {
		Processor processor = new Processor();
		Thread t1 = new Thread(() -> {
			try {
				processor.producer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread t2 = new Thread(() -> {
			try {
				processor.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		t1.start();
		t2.start();
	}
}
