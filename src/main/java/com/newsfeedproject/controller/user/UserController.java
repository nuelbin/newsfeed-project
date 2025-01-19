package com.newsfeedproject.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;
import com.newsfeedproject.common.session.SessionConst;
import com.newsfeedproject.dto.user.request.CreateUserRequestDto;
import com.newsfeedproject.dto.user.request.LoginUserRequestDto;
import com.newsfeedproject.dto.user.response.CreateUserResponseDto;
import com.newsfeedproject.dto.user.response.LoginUserResponseDto;
import com.newsfeedproject.dto.user.response.LogoutUserResponseDto;
import com.newsfeedproject.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponseDto> userSignupAPI(@RequestBody CreateUserRequestDto dto) {
		return new ResponseEntity<>(userService.userSignupService(dto), HttpStatus.CREATED);
	}

	// 회원 탈퇴

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<LoginUserResponseDto> userLoginAPI(
		@RequestBody LoginUserRequestDto dto,
		HttpServletRequest request
	) {
		User loginUser = userService.userLoginService(dto);

		// 로그인 성공 처리
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_USER_NAME, loginUser.getUserName());
		session.setAttribute(SessionConst.LOGIN_USER_ID, loginUser.getId());
		session.setAttribute(SessionConst.USER_STATUS, loginUser.getStatus());

		return new ResponseEntity<>(new LoginUserResponseDto(), HttpStatus.OK);
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<LogoutUserResponseDto> userLogoutAPI(HttpServletRequest request) {
		// 현재 세션 가져오기
		HttpSession session = request.getSession(false);

		if (session != null) {
			// 세션 무효화
			session.invalidate();
		}
		// 로그인한 상태에서만 로그아웃 되니까 null 인 경우는 필요 없지 않나?
		// 로그인 안 된 상태에서 로그아웃 누르면 로그인 먼저 하라고 예외처리 뜰텐데... todo 필요성 생각해 보기
		// if (session == null) {
		// 	throw new BaseException(ResponseCode.USER_NOT_FOUND);
		// todo - 만약 구현할 경우, ResponseCode에 세션값 없다는 예외처리 넣고 커스텀 예외처리 만들기
		// }

		return new ResponseEntity<>(new LogoutUserResponseDto(), HttpStatus.OK);
	}

	// 회원 다건 조회
}
