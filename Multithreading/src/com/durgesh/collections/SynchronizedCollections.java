package com.durgesh.collections;

import java.util.ArrayList;
import java.util.Collections;

public class SynchronizedCollections {
	
	public static void main(String[] args) {
		
		// add() and remove() methods are synchronized
		// intrinsic lock - not that efficient because threads
		// have to wait for each other even when they want to execute
		// independent methods (operations)
		// CONCURRENT COLLECTIONS!!!!
		var nums = Collections.synchronizedList(new ArrayList<Integer>());
		
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				nums.add(i);
			}
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				nums.add(i);
			}
		});
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Size of Array: " + nums.size());
	}
	
}
