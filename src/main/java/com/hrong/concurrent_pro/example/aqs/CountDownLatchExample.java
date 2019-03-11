package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CountDownLatchExample
 * @Date 2019/3/11 14:39
 * @Description
 *
 * CountDownLatch
 *
 * A synchronization aid that allows one or more threads to wait until
 * a set of operations being performed in other threads completes.
 * 一个多线程辅助工具，可以产生阻塞的效果，在初始化的时候传入一个值A，在各个线程
 * 进行逻辑处理的时候使用countDown()方法进行数值减1操作，再调用await()方法进
 * 行线程阻塞，限制之后的线程只有在A值down为0才能继续运行
 *
 * 支持限定时间的等待，等待的时间在await()中指定
 **/
@Slf4j
public class CountDownLatchExample {
	public final static int threadNumber = 200;

	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
		List<Integer> ids = new Vector<>();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < threadNumber; i++) {
			final int value = i;
			executorService.execute(() -> {
				try {
					deal(value);
				} catch (InterruptedException e) {
					log.error("err:{}", e);
				} finally {
					countDownLatch.countDown();
					ids.add(1);
				}
			});
		}
		countDownLatch.await(90, TimeUnit.MILLISECONDS);
		executorService.shutdown();
		log.info("Finish,total operations count is {}", ids.size());
	}

	private static void deal(int threadNumber) throws InterruptedException {
		log.info("threadNumber:{}", threadNumber);
		Thread.sleep(1000);
	}
}
