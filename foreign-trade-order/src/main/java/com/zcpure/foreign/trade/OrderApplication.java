package com.zcpure.foreign.trade;

import com.github.pagehelper.PageHelper;
import com.zcpure.foreign.trade.filter.HeaderInfoFilter;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
//@EnableEurekaClient
@EnableFeignClients(basePackages = "com.zcpure.foreign.trade.order.feign")
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean headerFilterRegistration(){
		FilterRegistrationBean registration = new FilterRegistrationBean(new HeaderInfoFilter());
		registration.addUrlPatterns("/*"); //
		registration.setName("headerInfoFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	PageHelper pageHelper() {
		//分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);

		//添加插件
		new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
		return pageHelper;
	}
}
