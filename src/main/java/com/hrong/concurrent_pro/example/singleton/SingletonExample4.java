package com.hrong.concurrent_pro.example.singleton;

import com.hrong.concurrent_pro.annotations.ThreadSafe;

/**
 * @ClassName SingletonExample4
 * @Date 2019/3/9 14:33
 * @Description 单例模式-懒汉模式之双重检测机制
 **/
@ThreadSafe
public class SingletonExample4 {
	//私有构造方法
	private SingletonExample4() {
		//TODO
	}
	//volatile关键字修饰的变量不允许线程内部进行缓存，也不允许指令重排序
	//使得该变量具有可见性
	private static volatile SingletonExample4 instance = null;

	public static SingletonExample4 getInstance() {
		if (instance == null) {
			synchronized (SingletonExample4.class) {
				if (instance == null) {
					instance = new SingletonExample4();
				}
			}
		}
		return instance;
	}
}
