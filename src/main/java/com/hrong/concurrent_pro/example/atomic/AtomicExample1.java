package com.hrong.concurrent_pro.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName AtomicExample1
 * @Date 2019/3/8 11:34
 * @Description	使用AtomicLong进行线程安全的计数操作
 **/
public class AtomicExample1 {
	public static int totalClient = 1000;
	public static int concurrentNumber = 50;
	public static AtomicLong count = new AtomicLong();

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(concurrentNumber);
		final CountDownLatch countDownLatch = new CountDownLatch(totalClient);
		for (int i = 0; i < totalClient; i++) {
			executorService.execute(() -> {
				try {
//					Acquires a permit, if one is available and returns immediately,
//     				reducing the number of available permits by one.
					semaphore.acquire();
					deal();
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		System.out.println("count:" + count.get());
	}

	public static void deal() {
		count.getAndIncrement();
	}
}

