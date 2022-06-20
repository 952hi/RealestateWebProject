package com.ssafy.vue.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.vue.dto.UserInfoDto;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserInfoDto userDto = (UserInfoDto) session.getAttribute("logincheck");

		if (userDto == null) {
			System.out.println(request.getRequestURI());
			session.setAttribute("nologin", true);
			
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		} else {
			return true;
		}

	}
}
