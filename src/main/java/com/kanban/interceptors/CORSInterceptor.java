package com.kanban.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CORSInterceptor implements HandlerInterceptor {
	private static final Logger log= LoggerFactory.getLogger(CORSInterceptor.class);
	
	public static final String REQUEST_ORIGIN_NAME = "Origin";
	public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
	public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
	public static final String METHODS_NAME = "Access-Control-Allow-Methods";
	public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
	public static final String MAX_AGE_NAME = "Access-Control-Max-Age";
	public static final String REQUEST_METHOD = "Access-Control-Request-Method";
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		response.setHeader(CREDENTIALS_NAME, "true");
		response.setHeader(METHODS_NAME, "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader(HEADERS_NAME, "Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader(MAX_AGE_NAME, "3600");
		response.setHeader(REQUEST_METHOD, "GET, POST, PUT, DELETE, OPTIONS");
		
		
		String origin = request.getHeader(REQUEST_ORIGIN_NAME);
		System.out.println("Received Origin "+ origin);
		if (origin == null || origin.equals("http://localhost:4200")) {
			response.setHeader(ORIGIN_NAME, origin);
			return true; // Proceed
		} else {
			log.warn("Attempted access from non-allowed origin: {}", origin);
			// Include an origin to provide a clear browser error
			response.setHeader(ORIGIN_NAME, origin);
			return false; // No need to find handler
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("Inside post handle");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {

		System.out.println("Inside after completion");
	}
}
