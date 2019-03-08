package com.hrong.concurrent_pro.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SynchronizedExample1
 * @Date 2019/3/8 16:15
 * @Description synchronized关键字的使用
 **/
@Slf4j
public class SynchronizedExample1 {
	/**
	 * synchronized关键字修饰【代码块】，
	 * 作用的对象是调用该方法的对象
	 *
	 * @param flag
	 */
	public void methodOne(int flag) {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				log.info("methodOne->{}->{}", flag, i);
			}
		}
	}

	/**
	 * synchronized关键字修饰【方法】，
	 * 作用的对象是调用该方法的对象
	 *
	 * @param flag
	 */
	public synchronized void methodTwo(int flag) {
		for (int i = 0; i < 10; i++) {
			log.info("methodTwo->{}->{}", flag, i);
		}
	}

	/**
	 * synchronized关键字修饰【代码块】，
	 * 作用的对象是所有对象
	 *
	 * @param flag
	 */
	public static synchronized void methodThree(int flag) {
		for (int i = 0; i < 10; i++) {
			log.info("methodThree->{}->{}", flag, i);
		}
	}

	/**
	 * synchronized关键字修饰类，
	 * 作用的对象是所有对象
	 *
	 * @param flag
	 */
	public static void methodFour(int flag) {
		synchronized (SynchronizedExample1.class) {
			for (int i = 0; i < 10; i++) {
				log.info("methodFour->{}->{}", flag, i);
			}
		}
	}

	/**
	 * synchronized关键字修饰类，
	 * 作用的对象是所有对象
	 *
	 * @param flag
	 */
	public void methodFive(int flag) {
		synchronized (SynchronizedExample1.class) {
			for (int i = 0; i < 10; i++) {
				log.info("methodFive->{}->{}", flag, i);
			}
		}
	}

	public static void main(String[] args) {
		SynchronizedExample1 example1 = new SynchronizedExample1();
		ExecutorService pool = Executors.newCachedThreadPool();

		/**
		 * 1、synchronized修饰代码块
		 */
		//因为作用的对象为调用该方法的对象，在此处调用该方法的为同一对象，所以会顺序执行
//		pool.execute(() -> {
//			example1.methodOne(1);
//		});
//		pool.execute(() -> {
//			example1.methodOne(2);
//		});

		//因为调用该方法的不是同一对象，所以存在交替调用的情况
//		SynchronizedExample1 example2 = new SynchronizedExample1();
//		pool.execute(() -> {
//			example1.methodOne(1);
//		});
//		pool.execute(() -> {
//			example2.methodOne(2);
//		});

		/**
		 * 2、synchronized修饰方法
		 */
		//因为作用的对象为调用该方法的对象，在此处调用该方法的为同一对象，所以会顺序执行
//		pool.execute(() -> {
//			example1.methodTwo(1);
//		});
//		pool.execute(() -> {
//			example1.methodTwo(2);
//		});

		//因为调用该方法的不是同一对象，所以存在交替调用的情况
//		SynchronizedExample1 example2 = new SynchronizedExample1();
//		pool.execute(() -> {
//			example1.methodTwo(1);
//		});
//		pool.execute(() -> {
//			example2.methodTwo(2);
//		});

		/**
		 * 3、synchronized修饰静态方法
		 */
		//作用的对象为所有对象，顺序执行
//		pool.execute(() -> {
//			methodThree(1);
//		});
//		pool.execute(() -> {
//			methodThree(2);
//		});
		/**
		 * 4、synchronized修饰类[方法为静态方法]
		 */
		//作用的对象为所有对象，顺序执行
//		pool.execute(() -> {
//			methodFour(1);
//		});
//		pool.execute(() -> {
//			methodFour(2);
//		});
		/**
		 * 4、synchronized修饰类[方法不为静态方法]
		 */
		//作用的对象为所有对象，顺序执行
//		SynchronizedExample1 example3 = new SynchronizedExample1();
//		pool.execute(() -> {
//			example1.methodFive(1);
//		});
//		pool.execute(() -> {
//			example3.methodFive(3);
//		});


		/**
		 * 子类继承父类
		 */
//		SynchronizedExample1Extend extend = new SynchronizedExample1Extend();
//		SynchronizedExample1Extend extend2 = new SynchronizedExample1Extend();
		/**
		 * 代用被synchronized修饰的代码块
		 */
		//同一对象调用-顺序执行
		//不同对象调用-乱序执行
//		pool.execute(() -> {
//			extend.methodOne(1);
//		});
//		pool.execute(() -> {
//			extend.methodOne(2);
//		});

		/**
		 * 代用被synchronized修饰的方法
		 */
		//同一对象调用-顺序执行
		//不同对象调用-乱序执行
//		pool.execute(() -> {
//			extend.methodTwo(1);
//		});
//		pool.execute(() -> {
//			extend.methodTwo(2);
//		});
	}
}

class SynchronizedExample1Extend extends SynchronizedExample1 {

}