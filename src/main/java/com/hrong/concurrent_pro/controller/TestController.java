package com.hrong.concurrent_pro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Date 2019/3/7 17:44
 * @Description
 **/
@RestController
@Slf4j
public class TestController {

	@RequestMapping(value = "/test")
	public String test() {
		return "hello";
	}
}
