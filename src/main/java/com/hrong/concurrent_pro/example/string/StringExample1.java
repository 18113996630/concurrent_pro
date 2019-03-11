package com.hrong.concurrent_pro.example.string;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName StringExample1
 * @Date 2019/3/10 16:24
 * @Description
 * StringBuilder是线程不安全的
 * StringBuffer是线程安全的
 **/
@Slf4j
public class StringExample1 {
	private static StringBuilder data1 = new StringBuilder();
	private static StringBuffer data2 = new StringBuffer();
	public static void main(String[] args) throws InterruptedException {
		int totalCount = 5000;
		int threadNumber = 200;
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalCount);
		for (int i = 0; i < totalCount; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
					dealWithStringBuffer();
					dealWithStringBuilder();
					semaphore.release();
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		countDownLatch.await();
		pool.shutdown();
		log.info("StringBuffer : {}", data2.length());
		log.info("StringBuilder : {}", data1.length());
	}

	private static void dealWithStringBuffer() {
		data2.append("1");
	}
	private static void dealWithStringBuilder() {
		data1.append("1");
	}
}
