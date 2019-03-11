package com.hrong.concurrent_pro.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName FutureExample
 * @Date 2019/3/11 17:51
 * @Description
 **/
@Slf4j
public class FutureExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> future = executorService.submit(new UserDefinedCall());
		log.info("the main thread start to compute other thing");
		Thread.sleep(3000);
		String result = future.get();
		log.info("the result of callable is : {}", result);
	}

	static class UserDefinedCall implements Callable<String> {
		@Override
		public String call() throws Exception {
			log.info("start to compute something...");
			Thread.sleep(2000);
			return "ok";
		}
	}
}


