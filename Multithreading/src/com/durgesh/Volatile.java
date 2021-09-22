package com.durgesh;

class Worker1 implements Runnable    {
	
	// It will be saved in the main memory
	private volatile boolean Terminated;
	@Override
	public void run() {
		while (!Terminated)   {
			System.out.println("Worker class is running");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isTerminated() {
		return Terminated;
	}
	
	public void setTerminated(boolean terminated) {
		Terminated = terminated;
	}
}

public class Volatile {
	public static void main(String[] args) {
		Worker1 worker1 = new Worker1();
		Thread t1 = new Thread(worker1);
		t1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		worker1.setTerminated(true);
		System.out.println("Algorithm is terminated");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
