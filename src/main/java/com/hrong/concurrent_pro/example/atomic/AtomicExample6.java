package com.hrong.concurrent_pro.example.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * @ClassName AtomicExample6
 * @Date 2019/3/8 11:34
 * @Description DoubleAccumulator
 **/
@Slf4j
public class AtomicExample6 {
	public static int totalCount = 101;
	public static int threadNumber = 10;
	@Getter
	public volatile Integer count = 0;
	//第一个参数是累加器的累加实现，第二个参数是累加器的初始值
	public static DoubleAccumulator accumulator = new DoubleAccumulator((left, right) -> left + right, 1000.0);

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalCount);
		for (int i = 0; i < totalCount; i++) {
			int finalI = i;
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					accumulator.accumulate(finalI);
					semaphore.release();
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		try {
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("totalValue:" + accumulator.get());
	}
}

