package com.durgesh;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker    {
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	
	public void producer() throws InterruptedException {
		lock.lock();
		System.out.println("Producer method.....");
		condition.await();
		System.out.println("Producer again....");
		lock.unlock();
	}
	
	public void consumer() throws InterruptedException {
		lock.lock();
		Thread.sleep(2000);
		System.out.println("Consumer Method....");
		condition.signal();
		lock.unlock();
	}
	
}

public class App4 {

	
//	public static void increment()  {
//		lock.lock();
//		try {
//			for (int i = 0; i < 10000; i++) {
//				counter++;
//			}
//		}
//		finally {
//			lock.unlock();
//		}
//	}
//
//	public static void add()    {
//		lock.unlock();
//	}
	public static void main(String[] args) {
		Worker worker = new Worker();
		Thread t1 = new Thread(() -> {
			try {
				worker.producer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread t2 = new Thread(() -> {
			try {
				worker.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		}   catch (InterruptedException e)  {
			e.printStackTrace();
		}
	}
}
