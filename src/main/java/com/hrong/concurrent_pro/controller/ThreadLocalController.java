package com.hrong.concurrent_pro.controller;

import com.hrong.concurrent_pro.example.threadLocal.RequestHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ThreadLocalController
 * @Date 2019/3/10 14:41
 * @Description
 **/
@Controller
@RequestMapping("/threadLocal")
public class ThreadLocalController {

	@RequestMapping("/get")
	@ResponseBody
	public Long threadLocal() {
		return RequestHolder.get();
	}
}
