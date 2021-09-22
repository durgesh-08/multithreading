package com.durgesh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 *  - Semaphore maintain a set of permits
 *  - acquire() -> if a permit is available then take it
 *  - release() -> adds a permit
 *
 *      Semaphores just keeps a count of the number available
 *      new Semaphore(int permits, boolean fair)!!!
 */

enum Downloader {
	INSTANCE;
	
	private Semaphore semaphore = new Semaphore(5, true);
	
	public void downloadData()  {
		try {
			semaphore.acquire();
			download();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			semaphore.release();
		}
	}
	
	private void download() {
		System.out.println("Downloading data from the web");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class Semaphores {
	
	
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i = 0;i<12;i++) {
			executorService.execute(() -> {
				Downloader.INSTANCE.downloadData();
			});
		}
		executorService.shutdown();
	}

}
