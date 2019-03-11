package com.hrong.concurrent_pro.filter;

import com.hrong.concurrent_pro.example.threadLocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName HttpFilterUserDefined
 * @Date 2019/3/10 14:18
 * @Description
 * filter:	filter是servlet中的一个组件
 * 			一般是在filter中对参数进行获取，比如token信息，或者是对参数进行过滤，比如过滤低俗信息
 *
 **/
@Slf4j
@Configuration
public class HttpFilterUserDefined implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//打印当前线程的ID与请求路径
		log.info("do filter, {}, {}", Thread.currentThread().getId(), request.getServletPath());
		//将当前线程ID添加到ThreadLocal中
		RequestHolder.add(Thread.currentThread().getId());
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

	//注册过滤器
	@Bean
	public FilterRegistrationBean httpFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new HttpFilterUserDefined());
		//设置要过滤的url
		registrationBean.addUrlPatterns("/threadLocal/*");
		return registrationBean;
	}
}
