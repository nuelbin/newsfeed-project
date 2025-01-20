package com.newsfeedproject.common.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import com.newsfeedproject.common.session.SessionConst;

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
		// HTTP 요청과 응답 객체로 변환 (ServletRequest와 ServletResponse는 HttpServletRequest/Response의 상위 인터페이스)
		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;

		// 클라이언트가 요청한 URI
		String requestURI = httpRequest.getRequestURI();

		try {
			// 화이트리스트에 포함되지 않은 URI라면 로그인 인증 체크 진행
			if (isLoginCheckPath(requestURI)) {
				// 세션이 존재하지 않거나 유효하지 않은 경우 처리
				if (!isSessionExists(httpRequest)) {
					// 응답의 Content-Type과 Character Encoding 설정
					httpResponse.setContentType("text/html; charset=UTF-8");
					httpResponse.setCharacterEncoding("UTF-8");

					// 로그인하지 않은 사용자에게 401 상태코드 반환
					httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpResponse.getWriter().write("먼저 로그인해 주세요.");
					return;
				}
			}
			// 인증 체크를 통과한 경우 다음 필터로 요청을 전달
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			// 필터 처리 중 발생한 예외를 상위로 던짐
			throw e;
		}
	}

	// 세션 존재 여부 확인
	private boolean isSessionExists(HttpServletRequest httpRequest) {
		// 현재 요청에 대한 세션 가져오기 (false: 세션이 없으면 새로 생성하지 않음)
		HttpSession session = httpRequest.getSession(false);

		// 세션이 존재하고, 세션에 로그인 사용자 정보(ID와 이름)가 모두 포함되어 있는지 확인
		return session != null
			&& session.getAttribute(SessionConst.LOGIN_USER_ID) != null
			&& session.getAttribute(SessionConst.LOGIN_USER_NAME) != null;
	}

	// URI가 화이트리스트에 포함되지 않는지 확인
	private boolean isLoginCheckPath(String requestURI) {
		// PatternMatchUtils.simpleMatch()를 사용해 URI가 화이트리스트에 일치하는지 확인
		// 일치하지 않는 경우에만 true 반환 (인증 체크가 필요한 경로라는 뜻)
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}