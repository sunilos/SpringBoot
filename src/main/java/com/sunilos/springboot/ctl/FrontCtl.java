package com.sunilos.springboot.ctl;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Front controller — validates JWT Bearer token on every protected request.
 *
 * Public paths (no token required):
 * POST /User/login
 * OPTIONS /** (CORS preflight)
 * GET /actuator/**
 *
 * All other paths require: Authorization: Bearer <token>
 *
 * On success the resolved loginId is stored in request attribute "loginId"
 * so downstream controllers can read it.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Component
@Order(1)
public class FrontCtl implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getRequestURI();
		String method = request.getMethod();

		// Allow CORS preflight
		if ("OPTIONS".equalsIgnoreCase(method)) {
			return true;
		}

		// Allow public endpoints
		if (isPublicPath(path, method)) {
			return true;
		}

		// Require Bearer token
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			sendUnauthorized(response, "Missing Authorization header");
			return false;
		}

		String token = authHeader.substring(7);
		if (!jwtUtil.validateToken(token)) {
			sendUnauthorized(response, "Invalid or expired token");
			return false;
		}

		// Make loginId available to downstream handlers
		request.setAttribute("loginId", jwtUtil.getLoginId(token));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {
	}

	// ----------------------------------------------------------------- private

	private boolean isPublicPath(String path, String method) {
		if (path.matches(".*/user/login.*"))
			return true;
		if (path.matches(".*/actuator.*"))
			return true;
		return false;
	}

	private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\":true,\"message\":\"" + message + "\"}");
	}
}
