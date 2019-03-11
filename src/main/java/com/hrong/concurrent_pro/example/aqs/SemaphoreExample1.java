package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CountDownLatchExample
 * @Date 2019/3/11 14:39
 * @Description
 *
 * Semaphore
 * 初始化的时候传入一个数值，指定最高并发数量
 * 使用acquire()获取许可，release()释放许可
 *
 **/
@Slf4j
public class SemaphoreExample1 {
	public final static int threadNumber = 30;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(5);
		for (int i = 0; i < threadNumber; i++) {
			final int value = i;
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					deal(value);
					semaphore.release();
				} catch (InterruptedException e) {
					log.error("err:{}", e);
				}
			});
		}
		executorService.shutdown();
	}

	private static void deal(int threadNumber) throws InterruptedException {
		log.info("threadNumber:{}", threadNumber);
		Thread.sleep(3000);
	}
}
