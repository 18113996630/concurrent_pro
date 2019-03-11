package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CyclicBarrierExample1
 * @Date 2019/3/11 16:16
 * @Description
 *
 * Creates a new CyclicBarrier that will trip when the
 * given number of parties (threads) are waiting upon it, and
 * does not perform a predefined action when the barrier is tripped.
 *
 **/
@Slf4j
public class CyclicBarrierExample1 {
	final static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
		log.info("all threads are ready for continuing, go ahead");
	});

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			Thread.sleep(1000);
			executorService.execute(() -> {
				deal(finalI);
			});
		}
		executorService.shutdown();
	}

	public static void deal(int number) {
		try {
			Thread.sleep(1000);
			log.info("thread {} is ready", number);
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			log.warn(e.getMessage());
		}
		log.info("thread {} continue", number);

	}
}
