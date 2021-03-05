package com.woniuxy.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient	//开启Eureka客户端
//@ServletComponentScan("com.woniuxy.user.filter")	//扫描过滤器
@MapperScan("com.woniuxy.user.mapper")
@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

//	// 配置跨域过滤器
//	@Bean
//	public FilterRegistrationBean<CrossFilter> registrationFilterBean() {
//		FilterRegistrationBean<CrossFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//		filterRegistrationBean.setFilter(new CrossFilter());
//		filterRegistrationBean.addUrlPatterns("/*");
//		filterRegistrationBean.setOrder(0);
//		filterRegistrationBean.setEnabled(true);
//		return filterRegistrationBean;
//	}

}
