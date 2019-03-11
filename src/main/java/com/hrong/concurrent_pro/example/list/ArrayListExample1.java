package com.hrong.concurrent_pro.example.list;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName ArrayListExample1
 * @Date 2019/3/11 09:43
 * @Description
 * ArrayList是线程不安全的，可能会产生异常，可能不会产生异常但是调用add()方法会产生线程安全问题
 * Stack继承Vector，两者都是线程安全的（在方法上添加了synchronized关键字），Vector是一个可变数组
 * 调用Collections.synchronizedXXX方法可获得线程安全的List
 **/
@Slf4j
public class ArrayListExample1 {
	//线程不安全
	//	private static List<Integer> list = new ArrayList();
	//线程安全
	private static List<Integer> list = Collections.synchronizedList(new ArrayList<>());
	private static Stack<Integer> stack = new Stack<>();
	private static Vector<Integer> vector = new Vector<>();

	public static void main(String[] args) throws InterruptedException {
		int totalCount = 5000;
		int threadNumber = 200;
		ExecutorService pool = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadNumber);
		CountDownLatch countDownLatch = new CountDownLatch(totalCount);
		for (int i = 0; i < totalCount; i++) {
			pool.execute(() -> {
				try {
					semaphore.acquire();
					dealWithArrayList();
					dealWithVector();
					dealWithStack();
					semaphore.release();
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		countDownLatch.await();
		pool.shutdown();
		log.info("ArrayList : {}", list.size());
		log.info("Vector : {}", vector.size());
		log.info("Stack : {}", stack.size());
	}

	private static void dealWithStack() {
		stack.add(1);
	}


	private static void dealWithVector() {
		vector.add(1);
	}

	private static void dealWithArrayList() {
		list.add(1);
	}
}
