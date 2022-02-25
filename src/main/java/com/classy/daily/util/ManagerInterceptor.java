package com.classy.daily.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 사용자 로그인 확인 인터셉터. HandlerInterceptorAdapter를 상속받아서 정의.
 */
public class ManagerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ManagerInterceptor.class);

	//콘트롤러의 메서드 실행 전에 처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.debug("ManagerInterceptor 실행");
		
		// 세션의 매니저 정보 읽기
		HttpSession session = request.getSession();
		
		int user_state = (int)session.getAttribute("user_state");
		
		if(user_state != 3 && session.getAttribute("loginId") == null) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		
		
		// 상속 관계에서만 객체 형 변환 가능!
		
		// 매니저가 아니면 돌려보냄
		if(user_state != 3) {
			response.sendRedirect(request.getContextPath() + "/member/afterLogin");
			return false;
		}
		
		// 매니저인 경우 요청한 경로로 진행
		return super.preHandle(request, response, handler);
	}

}
