package com.hrong.concurrent_pro.example.concurrent;

import com.hrong.concurrent_pro.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName ConcurrentHashMapExample
 * @Date 2019/3/11 11:01
 * @Description
 * key和value都不允许为空
 **/
@Slf4j
@ThreadSafe
public class ConcurrentHashMapExample {
	private static Map<Integer, Integer> map = new ConcurrentHashMap<>();

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
		log.info("set : {}", map.size());
	}

	private static void dealWithCopyOnWriteList() {
		map.put(1, 1);
	}
}
