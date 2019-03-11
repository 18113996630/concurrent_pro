package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

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
 **/
@Slf4j
public class SemaphoreExample2 {
	public final static int threadNumber = 10;

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(5);
		for (int i = 0; i < threadNumber; i++) {
			final int value = i;
			executorService.execute(() -> {
				try {
					//尝试获取semaphore，可以传入获取semaphore的数量，也可以传入等待的时间
					if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
						deal(value);
						semaphore.release();
					}else {
						log.info("fail to acquire the semaphore");
					}
				} catch (InterruptedException e) {
					log.error("err:{}", e);
				}
			});
		}
		executorService.shutdown();
	}

	private static void deal(int threadNumber) throws InterruptedException {
		log.info("threadNumber:{}", threadNumber);
//		Thread.sleep(3000);
	}
}
