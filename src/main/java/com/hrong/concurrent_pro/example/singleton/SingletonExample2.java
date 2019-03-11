package com.hrong.concurrent_pro.example.singleton;

/**
 * @ClassName SingletonExample2
 * @Date 2019/3/9 14:33
 * @Description 单例模式-饿汉模式
 * 饿汉模式的缺点:
 * 1、在私有构造中的处理逻辑过多可能会造成类加载的时候比较慢
 * 2、对象是在类加载的时候就被加载，如果后续不使用的话，会造成
 * 	  资源浪费，成本过高
 **/
public class SingletonExample2 {
	//私有构造方法
	private SingletonExample2(){
		//TODO
	}
	private static SingletonExample2 instance = new SingletonExample2();

	public static SingletonExample2 getInstance(){
		return instance;
	}
}
