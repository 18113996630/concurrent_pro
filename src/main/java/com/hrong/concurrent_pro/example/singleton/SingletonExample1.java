package com.hrong.concurrent_pro.example.singleton;

import com.hrong.concurrent_pro.annotations.ThreadUnSafe;

/**
 * @ClassName SingletonExample1
 * @Date 2019/3/9 14:33
 * @Description 单例模式-懒汉模式
 * 懒汉模式在多线程的情况下得到的对象不是单例的，
 * 因为可能有多个线程同时进入if判断
 **/
@ThreadUnSafe
public class SingletonExample1 {
	//私有构造方法
	private SingletonExample1(){}
	private static SingletonExample1 instance;

	public static synchronized SingletonExample1 getInstance(){
		if (instance == null) {
			instance = new SingletonExample1();
		}
		return instance;
	}
}
