package com.hrong.concurrent_pro.example.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName AtomicExample2
 * @Date 2019/3/8 11:34
 * @Description	AtomicReference
 **/
public class AtomicExample4 {
	private static Logger logger = LoggerFactory.getLogger(AtomicExample4.class);
	public static int totalClient = 1000;
	public static int concurrentNumber = 50;
	public static AtomicReference<Integer> reference = new AtomicReference<>(1);

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
		System.out.println("count:" + reference.get());
	}

	public static void deal() {
		if (reference.compareAndSet(1, 0)) {
			logger.info("change the int value from 1 to 0");
		}
		if (reference.compareAndSet(1, 2)) {
			logger.info("change the int value from 1 to 2");
		}
		if (reference.compareAndSet(2, 3)) {
			logger.info("change the int value from 2 to 3");
		}
		if (reference.compareAndSet(0, 9)) {
			logger.info("change the int value from 0 to 9");
		}
	}
}

