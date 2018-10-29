package com.zcpure.foreign.trade;

import com.zcpure.foreign.trade.filter.HeaderInfoFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zcpure.foreign.trade.user.feign")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean headerFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new HeaderInfoFilter());
		registration.addUrlPatterns("/*"); //
		registration.setName("headerInfoFilter");
		registration.setOrder(1);
		return registration;
	}
}
