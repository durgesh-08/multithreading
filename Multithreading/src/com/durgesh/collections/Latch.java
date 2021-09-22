package com.durgesh.collections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is used to synchronize one or more tasks by forcing them to wait for the completion of a set of operations
 * being performed by other tasks
 *
 *      - you give initial count to a CountDownLatch object, and any task that calls await() on that object will block until the count
 *      reaches zero
 *
 *      - other tasks may call countDown on the object to reduce the count, presumably when a task finishes its job
 *      - a CountDownLatch -> the count cannot be reset!!!!
 *          if you need a version that resets the count, you can use cyclic barrier instead
 *
 *      - the tasks that call countDown() are not blocked when they make that call.
 *          Only the call to await() is blocked until the count reaches zero
 *
 *      A typical use is to divide a problem into N independently  solvable tasks and create a CountDownLatch with a value of N
 *          when each task is finished it calls countDown() on the latch. Tasks waiting for the problem to be solved call await()
 *              on the latch to hold themselves back until it is completed
 *
 *      For example - you want to trigger something after 10 000 users download an Image !!!
 *
 */
public class Latch {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CountDownLatch countDownLatch = new CountDownLatch(5);
		for (int i = 0; i < 5; i++) {
			executorService.execute(new Worker(i + 1, countDownLatch));
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All the prerequisites are done....");
		executorService.shutdown();
	}
}

record Worker(int id, CountDownLatch countDownLatch) implements Runnable {
	
	@Override
	public void run() {
		doWork();
		countDownLatch.countDown();
	}
	
	private void doWork() {
		System.out.println("Thread with id: " + id + " starts working");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
