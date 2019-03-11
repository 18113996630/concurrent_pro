package com.hrong.concurrent_pro.example.singleton;

import com.hrong.concurrent_pro.annotations.Recommend;
import com.hrong.concurrent_pro.annotations.ThreadSafe;

/**
 * @ClassName SingletonExample5
 * @Date 2019/3/9 15:49
 * @Description 枚举是绝对单例
 * 通过将对象的实例化放在私有枚举类的构造器中，
 * 能保证对象只被初始化一次
 **/
@ThreadSafe
@Recommend
public class SingletonExample5 {
	private SingletonExample5() {
	}

	public static SingletonExample5 getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private enum Singleton{
		INSTANCE;
		private SingletonExample5 singletonExample5 = null;
		Singleton() {
			singletonExample5 = new SingletonExample5();
		}
		public SingletonExample5 getInstance(){
			return singletonExample5;
		}
	}
}
