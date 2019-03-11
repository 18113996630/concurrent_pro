package com.hrong.concurrent_pro.example.map;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName HashMapExample1
 * @Date 2019/3/11 10:05
 * @Description
 * HashMap是线程不安全的
 * HashTable是线程安全的（在方法上添加了synchronized关键字）
 **/
@Slf4j
public class HashMapExample1 {
	private static HashMap<Integer, Integer> map = new HashMap<>();
	private static Hashtable<Integer, Integer> hashTable = new Hashtable<>();

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
					dealWithHashMap();
					dealWithHashTable();
					semaphore.release();
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		countDownLatch.await();
		pool.shutdown();
		log.info("HashMap : {}", map.size());
		log.info("HashTable : {}", hashTable.size());
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			log.info("{}	{}", entry.getKey(), entry.getValue());
		}
	}

	private static void dealWithHashMap() {
		map.put(1, 1);
	}


	private static void dealWithHashTable() {
		hashTable.put(1, 1);
	}
}
