package com.hrong.concurrent_pro.example.atomic;

import com.hrong.concurrent_pro.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName AtomicExample3
 * @Date 2019/3/8 11:34
 * @Description	AtomicBoolean类
 * 	  A {@code boolean} value that may be updated atomically. See the
 *  * {@link java.util.concurrent.atomic} package specification for
 *  * description of the properties of atomic variables. An
 *  * {@code AtomicBoolean} is used in applications such as atomically
 *  * updated flags, and cannot be used as a replacement for a
 *  * {@link java.lang.Boolean}.
 **/
@ThreadSafe
public class AtomicExample3 {
	private static Logger log = LoggerFactory.getLogger(AtomicExample3.class);
	public static int totalClient = 1000;
	public static int concurrentNumber = 300;
	//默认值是false
	public static AtomicBoolean flag = new AtomicBoolean();
	//换成boolean时，会出现多个线程对flag进行修改的情况，程序的安全性无法得到保障
//	public static boolean flag = false;

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
		System.out.println("flag:" + flag.get());
//		System.out.println("flag:" + flag);
	}

	public static void deal() {
		if (flag.compareAndSet(false, true)){
//		if (!flag) {
//			flag = true;
			log.info("change the flag from false to true");
		}
	}
}

