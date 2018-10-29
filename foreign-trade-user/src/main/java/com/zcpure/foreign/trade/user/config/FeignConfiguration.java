package com.zcpure.foreign.trade.user.config;

import com.zcpure.foreign.trade.FeignClientErrorDecoder;
import com.zcpure.foreign.trade.interceptor.FeignClientRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignClientErrorDecoder();
	}

	@Bean
	public FeignClientRequestInterceptor myInterceptor(){
		return new FeignClientRequestInterceptor();
	}
}