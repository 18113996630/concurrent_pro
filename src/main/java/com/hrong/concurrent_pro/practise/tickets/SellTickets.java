package com.hrong.concurrent_pro.practise.tickets;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SellTickets
 * @Date 2019/3/12 10:58
 * @Description
 **/
@Slf4j
public class SellTickets {
	private static int tickets = 100;

	public static void sell(){
		tickets--;
	}
	public static void buy(){

	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		Semaphore semaphore = new Semaphore(1);
		CountDownLatch countDownLatch = new CountDownLatch(100);
		CountDownLatch count = countDownLatch;
		for (int i = 0; i < 100; i++) {
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					sell();
					Thread.sleep(100);
					log.info("{}买到一张票，还剩余:{}",Thread.currentThread().getName(),tickets);
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		log.info("还剩余{}",tickets);
	}
}
