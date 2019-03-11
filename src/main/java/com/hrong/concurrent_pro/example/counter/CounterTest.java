package com.hrong.concurrent_pro.example.counter;

import com.hrong.concurrent_pro.annotations.ThreadUnSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName ConterTest
 * @Date 2019/3/8 09:45
 * @Description
 **/
@ThreadUnSafe
public class CounterTest {
	public static int totalClient = 1000;
	public static int concurrentNumber = 50;
	public static int count = 0;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(concurrentNumber);
		final CountDownLatch countDownLatch = new CountDownLatch(totalClient);
		for (int i = 0; i < totalClient; i++) {
			executorService.execute(() -> {
				try {
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
		System.out.println("count:"+count);
	}

	public static void deal() {
		count++;
	}
}
