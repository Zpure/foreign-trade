package com.zcpure.foreign.trade.filter;

import com.alibaba.fastjson.JSON;
import com.zcpure.foreign.trade.*;
import com.zcpure.foreign.trade.dto.user.UserDTO;
import com.zcpure.foreign.trade.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if(request.getRequestURI().contains("/login") || !request.getRequestURI().startsWith("/api")) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			UserDTO user = (UserDTO) request.getSession().getAttribute(Const.LOGIN_TOKEN);
			if(user == null) {
				response.setContentType("application/json; charset=utf-8");
				response.setCharacterEncoding("UTF-8");

				WebJsonBean<Void> result = new WebJsonBean<>(BaseCode.USER_TOKEN_EXPIRE);
				String resultStr = JSON.toJSONString(result);
				OutputStream out = response.getOutputStream();
				out.write(resultStr.getBytes("UTF-8"));
				out.flush();
			} else {
				RequestThroughInfo userInfo = RequestThroughInfo.form(user);
				userInfo.setIp(RequestUtil.getIPAddress((HttpServletRequest) servletRequest));

				RequestThroughInfoContext.setInfo(userInfo);
				filterChain.doFilter(servletRequest, servletResponse);
				RequestThroughInfoContext.remove();
			}

		}

	}

	@Override
	public void destroy() {

	}
}
