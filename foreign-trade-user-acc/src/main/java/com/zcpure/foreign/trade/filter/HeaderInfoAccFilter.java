package com.zcpure.foreign.trade.filter;

import com.zcpure.foreign.trade.RequestThroughInfo;
import com.zcpure.foreign.trade.RequestThroughInfoContext;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ethan
 * @create_time 2018/10/23 17:20
 */
public class HeaderInfoAccFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(HeaderInfoAccFilter.class);

	private Boolean noLogin = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		String groupCode = request.getHeader("G-Token");
		UserDTO user = new UserDTO();
		user.setGroupCode(groupCode);

		RequestThroughInfo userInfo = RequestThroughInfo.form(user);
		userInfo.setIp(RequestUtil.getIPAddress((HttpServletRequest) servletRequest));
		userInfo.setRequestId(MDC.get("requestId"));

		RequestThroughInfoContext.setInfo(userInfo);
		filterChain.doFilter(servletRequest, servletResponse);
		RequestThroughInfoContext.remove();

	}

	@Override
	public void destroy() {

	}
}
