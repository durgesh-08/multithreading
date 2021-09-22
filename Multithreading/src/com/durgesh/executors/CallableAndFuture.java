package com.durgesh.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Processor implements Callable<String> {
	
	private int id;
	
	public Processor(int id) {
		this.id = id;
	}
	
	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return "Id: " + id;
	}
}


public class CallableAndFuture {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Future<String>> futureList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Future<String> future = executorService.submit(new Processor(i + 1));
			futureList.add(future);
		}
		futureList.stream().forEach(stringFuture -> {
			try {
				System.out.println(stringFuture.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		executorService.shutdown();
	}
}
