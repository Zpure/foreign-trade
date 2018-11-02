package com.zcpure.foreign.trade.interceptor;

import com.alibaba.fastjson.JSON;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author ethan
 * @create_time 2018/10/23 16:58
 */
public class FeignClientRequestInterceptor implements RequestInterceptor {
	private static final Logger log = LoggerFactory.getLogger(FeignClientRequestInterceptor.class);

	@Override
	public void apply(RequestTemplate requestTemplate) {
		//从应用上下文中取出信息，放入Feign的请求头中
		RequestThroughInfo info = RequestThroughInfoContext.getInfo();
		if (info != null) {
			try {
				String infoStr = JSON.toJSONString(info);
				requestTemplate.header(RequestThroughInfoContext.KEY_INFO_IN_HTTP_HEADER,
					new String[]{URLEncoder.encode(infoStr, "UTF-8")});
			} catch (UnsupportedEncodingException e) {
				log.error("信息设置错误", e);
			}
		}
	}
}
