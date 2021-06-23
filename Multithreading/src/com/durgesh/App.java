package com.durgesh;

class Runner1 implements Runnable	{

	@Override
	public void run() {
		for(int i =0;i<10; ++i)	{
			System.out.println("Runner1: " +i);
		}
	}
}

class Runner2 implements Runnable	{

	@Override
	public void run() {
		for(int i =0;i<10; ++i)	{
			System.out.println("Runner2: " +i);
		}
	}
}

public class App {

	public static void main(String[] args) {
		
		Runner1 runner1 = new Runner1();
		Runner2 runner2 = new Runner2();
		
		(new Thread(runner1)).start();
		(new Thread(runner2)).start();
		
	}
	
}
