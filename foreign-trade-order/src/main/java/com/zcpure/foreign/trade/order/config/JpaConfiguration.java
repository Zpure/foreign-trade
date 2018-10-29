package com.zcpure.foreign.trade.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ethan
 * @create_time 2018/10/22 17:11
 */
@Configuration
public class JpaConfiguration {
	@Bean
	public OpenEntityManager openEntityManager() {
		return new OpenEntityManager();
	}
}
