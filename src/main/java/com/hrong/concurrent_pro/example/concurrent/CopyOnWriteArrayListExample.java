package com.hrong.concurrent_pro.example.concurrent;

import com.hrong.concurrent_pro.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName CopyOnWriteArrayListExample
 * @Date 2019/3/11 11:01
 * @Description
 **/
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {
	private static List<Integer> list = new CopyOnWriteArrayList<>();

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
					dealWithCopyOnWriteList();
					semaphore.release();
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		countDownLatch.await();
		pool.shutdown();
		log.info("ArrayList : {}", list.size());
	}

	private static void dealWithCopyOnWriteList() {
		list.add(1);
	}
}
