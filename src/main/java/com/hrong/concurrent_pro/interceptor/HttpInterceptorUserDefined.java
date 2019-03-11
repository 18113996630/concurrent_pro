package com.hrong.concurrent_pro.interceptor;

import com.hrong.concurrent_pro.example.threadLocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName HttpInterceptor
 * @Date 2019/3/10 14:32
 * @Description
 * interceptor:spring的一个组件，主要在面向切面编程中进行使用
 * 			    针对方法执行前，执行后，调用完成可以加入额外的逻辑
 **/
@Slf4j
@Configuration
public class HttpInterceptorUserDefined extends HandlerInterceptorAdapter implements WebMvcConfigurer {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("prehandle");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		RequestHolder.remove();
		log.info("remove the requestId,so the value of the requestId is:{}",RequestHolder.get());
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpInterceptorUserDefined()).addPathPatterns("/**");
	}
}
