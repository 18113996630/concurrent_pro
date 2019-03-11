package com.hrong.concurrent_pro.example.singleton;

import com.hrong.concurrent_pro.annotations.ThreadUnSafe;

/**
 * @ClassName SingletonExample3
 * @Date 2019/3/9 14:33
 * @Description 单例模式-懒汉模式之双重检测机制
 * 不是线程安全的原因：
 * instance = new SingletonExample3();
 * 上句代码需要进行的操作
 * 1、声明内存空间
 * 2、对象的实例化
 * 3、将instance指向声明的内存空间
 * -----------------------------------------
 * 因为cpu指令存在指令重排序优化的策略，所有在不影响结果的前提下实际可能发生的操作是
 * 1、声明内存空间
 * 2、将instance指向声明的内存空间
 * 3、对象的实例化
 * 当进入同步代码块的线程执行了12指令，而另一线程刚好进入第一个if判断，
 * 这时if为false，直接将未完成初始化的对象进行返回，则会出现线程问题
 * *************************************************出现线程安全的可能性较低******************************************************
 **/
@ThreadUnSafe
public class SingletonExample3 {
	//私有构造方法
	private SingletonExample3() {
		//TODO
	}

	private static SingletonExample3 instance = null;

	public static SingletonExample3 getInstance() {
		if (instance == null) {
			synchronized (SingletonExample3.class) {
				if (instance == null) {
					instance = new SingletonExample3();
				}
			}
		}
		return instance;
	}
}
