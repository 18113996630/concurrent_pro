package com.hrong.concurrent_pro.example.concurrent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName VectorExample1
 * @Date 2019/3/11 10:49
 * @Description
 **/
public class CopyOnWriteArrayListExample1 {

	public static void main(String[] args) {
		List<Integer> vector = new CopyOnWriteArrayList<>();
		while (true) {
			for (int i = 0; i < 10; i++) {
				vector.add(i);
			}
			new Thread(() -> {
				for (int i = 0; i < vector.size(); i++) {
					vector.remove(i);
				}
			}).start();
			new Thread(() -> {
				for (int i = 0; i < vector.size(); i++) {
					vector.get(i);
				}
			}).start();
		}

	}
}
