package com.hrong.concurrent_pro.example.counter;

import com.hrong.concurrent_pro.annotations.ThreadUnSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SynchronizedCounter
 * @Date 2019/3/8 16:59
 * @Description volatile实现计数功能
 **/
@Slf4j
@ThreadUnSafe
public class VolatileCounter {
	public static int totalNumber = 5000;
	public static int threadNumber = 100;
	public static volatile int count = 0;

	public static void main(String[] args) {
		VolatileCounter example1 = new VolatileCounter();
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalNumber);
		long start = System.currentTimeMillis();
		for (int i = 0; i < totalNumber; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
					example1.methodOne();
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
			long end = System.currentTimeMillis();
			log.info("cost time:{}", end - start);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修饰代码块
	 */
	public void methodOne() {
		//1、从主存中获取count的值赋值到工作内存中
		//2、执行+1操作
		//3、将工作内存中的count值写入到主存
		//当两个线程同时从主存中获取到最新的值，并进行+1操作，
		//往主存中写的时候就会丢失掉一次的加操作，使得最后的结果
		//不是最开始想要的
		count++;
	}
}





