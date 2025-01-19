package com.newsfeedproject.common.filter;

import java.io.IOException;

import com.newsfeedproject.common.session.SessionConst;
import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {
	// 화이트리스트: 로그인 인증 체크가 불필요한 URI
	private static final String[] whiteList = {"/api/users/signup", "/api/users/login"};

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
		// 클라이언트가 요청한 URI
		String requestURI = httpRequest.getRequestURI();

		try {
			// 로그인 인증 체크 시작
			if (isLoginCheckPath(requestURI)) {

				// 인증되지 않은 상태 처리
				if (!isSessionExists(httpRequest)) {
					httpResponse.setContentType("text/html; charset=UTF-8");
					httpResponse.setCharacterEncoding("UTF-8");
					// 로그인하지 않은 사용자에게 401 status 반환
					httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpResponse.getWriter().write("로그인하지 않았습니다. 먼저 로그인해주세요.");
					return;
				}
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			throw e;
		}
	}

	// 세션이 존재하는지 확인
	private boolean isSessionExists(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		return session != null && session.getAttribute(SessionConst.LOGIN_USER_ID) != null
			&& session.getAttribute(SessionConst.LOGIN_USER_NAME) != null;
	}

	// 인증 체크를 해야 하는 URI인지 확인하는 메서드
	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}