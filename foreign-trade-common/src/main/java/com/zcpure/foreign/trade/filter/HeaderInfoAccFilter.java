package com.zcpure.foreign.trade.filter;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ethan
 * @create_time 2018/10/23 17:20
 */
public class HeaderInfoAccFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(HeaderInfoAccFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		RequestThroughInfo userInfo = RequestThroughInfo.defaultInfo();
		userInfo.setIp(RequestUtil.getIPAddress((HttpServletRequest) servletRequest));
		RequestThroughInfoContext.setInfo(userInfo);
		filterChain.doFilter(servletRequest, servletResponse);
		RequestThroughInfoContext.remove();
	}

	@Override
	public void destroy() {

	}
}
