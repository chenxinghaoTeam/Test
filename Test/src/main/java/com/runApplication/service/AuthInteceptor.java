package com.runApplication.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器
 */
public class AuthInteceptor implements HandlerInterceptor {

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 * @throws IOException 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
			// 统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
			String name =   (String) request.getSession().getAttribute("name");
			if (null == name) {
				response.reset();
				response.sendRedirect("/login/index.html");
				return false;
			}else {
				return true;
			}
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
	}

	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 * @throws IOException 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws IOException {
		String name =   (String) request.getSession().getAttribute("out");
		if ("out".equals(name)) {
			request.getSession().invalidate();//清空系统中所有的session
			response.sendRedirect("/login/index.html");
			return;
		}
	}

}
