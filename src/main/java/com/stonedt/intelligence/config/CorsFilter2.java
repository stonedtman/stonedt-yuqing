package com.stonedt.intelligence.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @date 2020年5月25日 下午5:52:52
 */
@WebFilter(filterName = "CorsFilter")
@Configuration
public class CorsFilter2 implements Filter {

	@Value("${xy.cors-white-list}")
	private String whiteList;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("Origin");
		List<String> collect = Arrays.stream(whiteList.split(",")).collect(Collectors.toList());
		response.setHeader("Access-Control-Allow-Origin",collect.contains(origin) ? origin : "-");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");
		//设置date头信息，RCF822格式的时间字符串
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz",Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		response.setHeader("Date", sdf.format(new Date()));

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}
}