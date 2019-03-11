package com.hrong.concurrent_pro.example.counter;

import com.hrong.concurrent_pro.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SynchronizedCounter
 * @Date 2019/3/8 16:59
 * @Description synchronized实现计数功能
 **/
@Slf4j
@ThreadSafe
public class SynchronizedCounter {
	public static int totalNumber = 50000;
	public static int threadNumber = 100;
	public static int count = 0;

	public static void main(String[] args) {
//		Set<String> threads = new TreeSet<>((o1, o2) -> {
//			Integer thread_name_1 = Integer.valueOf(o1.split("-")[3]);
//			Integer thread_name_2 = Integer.valueOf(o2.split("-")[3]);
//			return thread_name_1-thread_name_2;
//		});
//		Lock lock = new ReentrantLock();
		SynchronizedCounter example1 = new SynchronizedCounter();
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalNumber);
		long start = System.currentTimeMillis();
		for (int i = 0; i < totalNumber; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
//					lock.lock();
					example1.methodOne();
//					threads.add(Thread.currentThread().getName());
//					lock.unlock();
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
//			for (String thread : threads) {
//				log.info(thread);
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修饰代码块
	 */
	public void methodOne() {
		synchronized (this) {
			count++;
		}
	}
}





