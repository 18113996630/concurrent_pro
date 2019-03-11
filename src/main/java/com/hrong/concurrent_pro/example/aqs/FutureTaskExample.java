package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName FutureTaskExample
 * @Date 2019/3/11 18:07
 * @Description
 **/
@Slf4j
public class FutureTaskExample {
	public static void main(String[] args) throws Exception {
		FutureTask<String> task = new FutureTask<>(() -> {
			log.info("start to compute something...");
			Thread.sleep(2000);
			return "finish";
		});
		new Thread(task).start();
		log.info("the main thread start to compute other thing");
		Thread.sleep(3000);
		String result = task.get();
		log.info("the result of callable is : {}", result);
	}
}
