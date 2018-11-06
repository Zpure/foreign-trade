package com.zcpure.foreign.trade.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

	@Bean
	public MappingJackson2HttpMessageConverter MappingJsonpHttpMessageConverter() {
		jackson2ObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
		ObjectMapper mapper = jackson2ObjectMapperBuilder.build(); // ObjectMapper为了保障线程安全性，里面的配置类都是一个不可变的对象

		String formart = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat fmt = new SimpleDateFormat(formart);
		mapper.setDateFormat(fmt);
		MappingJackson2HttpMessageConverter mappingJsonpHttpMessageConverter = new MappingJackson2HttpMessageConverter(
				mapper);
		return mappingJsonpHttpMessageConverter;
	}
/*

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/admin/*").allowedOrigins("http://localhost:8080")
			.allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
			.allowCredentials(true).maxAge(3600);
	}
*/

}