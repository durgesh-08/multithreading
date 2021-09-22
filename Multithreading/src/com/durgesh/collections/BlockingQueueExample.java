package com.durgesh.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * {@link BlockingQueue} -> an interface that represents a queue that is thread safe
 *      put items or take items from it...
 *
 *      for example: one thread putting items into the queue and another thread taking items from it
 *          at the same time !!!
 *              We can do it with producer-consumer pattern!!!
 *
 *
 *      put() - putting items to the queue
 *      take() - taking items from the queue
 *
 */

record FirstWorker(BlockingQueue<Integer> blockingQueue) implements Runnable    {
	
	@Override
	public void run() {
		int counter = 0;
		while(true) {
			try {
				blockingQueue.put(counter);
				System.out.println("Putting items in to the queue..." + counter);
				counter++;
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

record SecondWorker(BlockingQueue<Integer> blockingQueue) implements Runnable    {
	
	@Override
	public void run() {
		while(true) {
			try {
				int number = blockingQueue.take();
				System.out.println("Taking number out of the queue..." + number);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class BlockingQueueExample {
	
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		FirstWorker firstWorker = new FirstWorker(queue);
		SecondWorker secondWorker = new SecondWorker(queue);
		new Thread(firstWorker).start();
		new Thread(secondWorker).start();
	}
	
}
