package com.hrong.concurrent_pro.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName AtomicExample2
 * @Date 2019/3/8 11:34
 * @Description AtomicReferenceFieldUpdater
 **/
@Slf4j
public class AtomicExample5 {
	@Getter
	public volatile Integer count = 0;
	public static AtomicReferenceFieldUpdater updater =
			AtomicReferenceFieldUpdater.newUpdater(AtomicExample5.class, Integer.class, "count");

	public static void main(String[] args) {
		AtomicExample5 example5 = new AtomicExample5();
		if (updater.compareAndSet(example5, 0, 10)) {
			log.info("the count is 0,change it to 10 now");
		}
		log.info("the result is:"+example5.count);
	}
}

