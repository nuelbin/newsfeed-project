package com.newsfeedproject.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.newsfeedproject.common.filter.LoginCheckFilter;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig {
	// 로그인 상태 체크 필터 등록
	@Bean
	public FilterRegistrationBean loginCheckFilter() {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/*");

		return filterFilterRegistrationBean;
	}
}
