package com.hrong.concurrent_pro.example.threadLocal;

/**
 * @ClassName RequestHolder
 * @Date 2019/3/10 14:15
 * @Description
 **/
public class RequestHolder {
	private final static ThreadLocal<Long> REQUEST_HOLDER = new ThreadLocal<>();

	public static void add(Long requestId) {
		REQUEST_HOLDER.set(requestId);
	}

	public static Long get() {
		Long requestId = REQUEST_HOLDER.get();
		return requestId;
	}

	public static void remove() {
		REQUEST_HOLDER.remove();
	}
}
