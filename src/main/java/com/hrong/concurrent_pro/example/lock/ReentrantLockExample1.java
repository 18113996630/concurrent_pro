package com.hrong.concurrent_pro.example.lock;

import com.hrong.concurrent_pro.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockExample1
 * @Date 2019/3/8 16:59
 * @Description ReentrantLock实现计数功能
 **/
@Slf4j
@ThreadSafe
public class ReentrantLockExample1 {
	public static int totalNumber = 50000;
	public static int threadNumber = 100;
	public static int count = 0;
	static Lock lock = new ReentrantLock(true);

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalNumber);
		for (int i = 0; i < totalNumber; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
					methodOne();
					semaphore.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		try {
			countDownLatch.await();
			pool.shutdown();
			log.info("the result of method is {}", count);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	public static void methodOne() {
		lock.lock();
		try {
			count++;
		}finally {
			lock.unlock();
		}
	}
}





