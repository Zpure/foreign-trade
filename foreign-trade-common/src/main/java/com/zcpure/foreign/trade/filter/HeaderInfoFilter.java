package com.zcpure.foreign.trade.filter;

import com.alibaba.fastjson.JSON;
import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author ethan
 * @create_time 2018/10/23 17:20
 */
public class HeaderInfoFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(HeaderInfoFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String headerInfo = request.getHeader(RequestThroughInfoContext.KEY_INFO_IN_HTTP_HEADER);
		if (StringUtils.isNotBlank(headerInfo)) {
			try {
				headerInfo = URLDecoder.decode(headerInfo, "UTF-8");
				RequestThroughInfo userInfo = JSON.parseObject(headerInfo, RequestThroughInfo.class);
				//将UserInfo放入上下文中
				RequestThroughInfoContext.setInfo(userInfo);
			} catch (UnsupportedEncodingException e) {
				log.error("init userInfo error", e);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
		RequestThroughInfoContext.remove();
	}

	@Override
	public void destroy() {

	}
}
