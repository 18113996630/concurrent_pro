package com.hrong.concurrent_pro.example.singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SingletonTest
 * @Date 2019/3/9 14:39
 * @Description 测试单例模式
 **/
@Slf4j
public class SingletonTest {
	private static int totalCount = 10000;
	private static int threadNumber = 2000;
	private static Set<SingletonExample5> examples = new HashSet<>();


	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalCount);
		for (int i = 0; i < totalCount; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
					deal();
					semaphore.release();
				} catch (InterruptedException e) {
					log.error("exception:{}", e);
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		pool.shutdown();
		for (SingletonExample5 example : examples) {
			log.info("hashcode:{}",example.hashCode());
		}
	}

	//调用静态工厂方法获取对象，将对象加入set集合
	public static void deal() {
		SingletonExample5 instance = SingletonExample5.getInstance();
		examples.add(instance);
	}
}
