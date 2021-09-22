package com.durgesh;

public class App2 {
	
	public static int counter1 = 0;
	public static int counter2 = 0;
	
	private static final Object lock1 = new Object();
	private static final Object lock2 = new Object();
	
	/*
	We have to make sure that this method is
	only used by one thread at a time.
	*/
	/*
	Because App2 object has a single monitor lock: this is why
	the methods cannot be executed at "the same time". - the time slicing algorithm
	 */
	/*
	Usually it is not a good practice to use synchronized keyword
	 */
	public static void increment1()  {
		// Class level locking
//		synchronized (App2.class) {
//			counter1++;
//		}
		
		synchronized (lock1) {
			counter1++;
		}
	}
	
	public static void increment2()  {
		// rule of thumb: we synchronize blocks that are 100% necessary
//		synchronized (App2.class) {
//			counter2++;
//		}
		synchronized (lock2) {
			counter2++;
		}
	}
	
	public static void process() {
		
		Runnable r1 = () -> {
			for (int i = 0; i < 300; i++) {
				increment1();
			}
		};
		
		Runnable r2 = () -> {
			for (int i = 0; i < 300; i++) {
				increment2();
			}
		};
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("The counter is: " + counter1);
		System.out.println("The counter is: " + counter2);
	}
	
	public static void main(String[] args) {
		process();
	}
}
