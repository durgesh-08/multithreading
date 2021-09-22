package com.durgesh.collections;

import java.util.Random;
import java.util.concurrent.*;

/**
 *
 *  Latch --> multiple threads can wait for each other
 *
 * A {@link CyclicBarrier} is used in situations where you want to create a group of tasks
 *  to perform work in parallel + wait until they are all finished before moving on to the next step
 *      -> something like join()
 *      -> something like {@link CountDownLatch}
 *
 *      {@link CountDownLatch}: one shot event
 *      {@link CyclicBarrier}: it can be reused over and over again
 *
 *              + {@link CyclicBarrier} has a barrier action: a {@link Runnable}, that will run automatically
 *                  when the barrier count reaches zero
 *
 * new CyclicBarrier(N) -> N threads will wait for each other
 *
 *      We cannot reuse latches, but we reuse {@link CyclicBarrier}!!!!
 */
record BarrierWorker(int id, Random random, CyclicBarrier cyclicBarrier) implements Runnable {
	
	public BarrierWorker(int id, CyclicBarrier cyclicBarrier)  {
		this(id, new Random(), cyclicBarrier);
	}
	
	@Override
	public void run() {
		doWork();
	}
	
	private void doWork() {
		System.out.println("Thread with id: " + id + " starts the task");
		try {
			Thread.sleep(random.nextInt(3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread with id: " + id + " finished...");
		try {
			cyclicBarrier.await();
			System.out.println("After await");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "id=" + id;
	}
}

public class CyclicBarrierExample {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, () -> {
			System.out.println("All the tasks are finished!");
		});
		for (int i = 0; i < 5; i++) {
			executorService.execute(new BarrierWorker(i+1, barrier));
		}
		executorService.shutdown();
	}
}
