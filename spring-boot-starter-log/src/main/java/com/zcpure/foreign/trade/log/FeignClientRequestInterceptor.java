package com.zcpure.foreign.trade.log;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ethan
 * @create_time 2018/10/23 16:58
 */
@Configuration
public class FeignClientRequestInterceptor implements RequestInterceptor {
	private static final Logger log = LoggerFactory.getLogger(FeignClientRequestInterceptor.class);

	private static final String REQUEST_ID_KEY = "requestId";

	@Override
	public void apply(RequestTemplate requestTemplate) {
		try {
			String requestId = LogContext.getRequestId();
			requestTemplate.header(REQUEST_ID_KEY,
				new String[]{URLEncoder.encode(requestId, "UTF-8")});
		} catch (UnsupportedEncodingException e) {
			log.error("信息设置错误", e);
		}
	}
}
