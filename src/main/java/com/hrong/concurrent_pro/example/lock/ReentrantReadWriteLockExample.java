package com.hrong.concurrent_pro.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReentrantReadWriteLockExample
 * @Date 2019/3/11 17:03
 * @Description
 *
 * ReentrantReadWriteLock内部有读锁和写锁
 * 要想获得写锁进行数据的写入的时候，必须等待
 * 所有的读锁都已经是unlock状态（悲观机制）
 *
 **/
@Slf4j
public class ReentrantReadWriteLockExample {
	private static Map<String,Student> datas = new HashMap<>();
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static Lock readLock = lock.readLock();
	private static Lock writeLock = lock.writeLock();

	public static Student getStudent(String key) {
		readLock.lock();
		try {
			return datas.get(key);
		}finally {
			readLock.unlock();
		}
	}
	public static Collection<Student> getStudents() {
		readLock.lock();
		try {
			return datas.values();
		}finally {
			readLock.unlock();
		}
	}

	public static void put(String key, Student student) {
		writeLock.lock();
		try {
			datas.put(key, student);
		}finally {
			writeLock.unlock();
		}
	}


}
class Student{}
