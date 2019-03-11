package com.hrong.concurrent_pro.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName AtomicExample2
 * @Date 2019/3/8 11:34
 * @Description	使用AtomicLong进行线程安全的计数操作
 * 	  This class is usually preferable to {@link AtomicLong} when
 *  * multiple threads update a common sum that is used for purposes such
 *  * as collecting statistics, not for fine-grained synchronization
 *  * control.  Under low update contention, the two classes have similar
 *  * characteristics. But under high contention, expected throughput of
 *  * this class is significantly higher, at the expense of higher space
 *  * consumption.
 * 	LongAdder这个类在多个线程用于数字求和，统计信息的时候表现的比AtomicLong好，在细粒度同步
 * 	控制中则表现稍逊。线程并发数不高，竞争不激烈的时候，这两个类表现相差无几，但是
 * 	并发量变大的时候，LongAdder则表现的更好，代价是消耗了更多的空间资源
 **/
public class AtomicExample2 {
	public static int totalClient = 1000;
	public static int concurrentNumber = 50;
	public static LongAdder count = new LongAdder();

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(concurrentNumber);
		final CountDownLatch countDownLatch = new CountDownLatch(totalClient);
		for (int i = 0; i < totalClient; i++) {
			executorService.execute(() -> {
				try {
//					Acquires a permit, if one is available and returns immediately,
//     				reducing the number of available permits by one.
					semaphore.acquire();
					deal();
					semaphore.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		System.out.println("count:" + count.longValue());
	}

	public static void deal() {
		count.increment();
	}
}

