package com.hrong.concurrent_pro.example.list;

import java.util.Vector;

/**
 * @ClassName VectorExample1
 * @Date 2019/3/11 10:49
 * @Description
 **/
public class VectorExample1 {

	public static void main(String[] args) {
		Vector<Integer> vector = new Vector<>();
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
