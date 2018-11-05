package com.zcpure.foreign.trade.task.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ethan
 * @create_time 2018/11/5 10:25
 */
@Component
public class OrderTask {
	private static Logger log = LoggerFactory.getLogger(OrderTask.class);

	@Scheduled(cron = " 0 0 2 * * ? ")
	public void test() {
		log.info("========================test===========================");
	}
}
