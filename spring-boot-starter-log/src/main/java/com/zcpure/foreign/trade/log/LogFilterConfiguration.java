package com.zcpure.foreign.trade.log;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author ethan
 * @create_time 2018/10/23 17:20
 */
@Configuration
public class LogFilterConfiguration {

	@Bean
	public FilterRegistrationBean filterRegistrationBean1() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new Filter() {
			private static final String REQUEST_ID_KEY = "requestId";

			@Override
			public void init(FilterConfig filterConfig) throws ServletException {

			}

			@Override
			public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) servletRequest;
				String requestId = request.getHeader(REQUEST_ID_KEY);
				if (StringUtils.isNotBlank(requestId)) {
					try {
						requestId = URLDecoder.decode(requestId, "UTF-8");
						LogContext.setRequestId(requestId);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					LogContext.createRequestIfNecessary();
				}
				filterChain.doFilter(servletRequest, servletResponse);
			}

			@Override
			public void destroy() {

			}
		});
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setOrder(6);//order的数值越小 则优先级越高
		return filterRegistrationBean;
	}
}
