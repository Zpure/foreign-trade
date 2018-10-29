package com.zcpure.foreign.trade;

import com.zcpure.foreign.trade.filter.HeaderInfoAccFilter;
import com.zcpure.foreign.trade.interceptor.FeignClientRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zcpure.foreign.trade.feign")
public class AdminAccApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminAccApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean headerFilterRegistration(){
		FilterRegistrationBean registration = new FilterRegistrationBean(new HeaderInfoAccFilter());
		registration.addUrlPatterns("/*");
		registration.setName("headerInfoFilter");
		registration.setOrder(1);
		return registration;
	}
}
