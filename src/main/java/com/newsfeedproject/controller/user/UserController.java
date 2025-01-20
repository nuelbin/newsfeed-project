package com.newsfeedproject.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.user.UnauthorizedUserException;
import com.newsfeedproject.common.session.SessionConst;
import com.newsfeedproject.dto.user.request.CreateUserRequestDto;
import com.newsfeedproject.dto.user.request.DeleteUserRequestDto;
import com.newsfeedproject.dto.user.request.LoginUserRequestDto;
import com.newsfeedproject.dto.user.response.CreateUserResponseDto;
import com.newsfeedproject.dto.user.response.DeleteUserResponseDto;
import com.newsfeedproject.dto.user.response.FindUserResponseDto;
import com.newsfeedproject.dto.user.response.LoginUserResponseDto;
import com.newsfeedproject.dto.user.response.LogoutUserResponseDto;
import com.newsfeedproject.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	@DeleteMapping
	public ResponseEntity<DeleteUserResponseDto> userDeleteAPI(
		@RequestBody DeleteUserRequestDto dto,
		HttpServletRequest request
	) {
		// 현재 세션 가져오기
		HttpSession session = request.getSession(false);

		if (session == null) {
			throw new UnauthorizedUserException();
		}

		// 세션에서 userId 가져오기
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);

		// 서비스에서 삭제 처리
		userService.userDeleteService(userId, dto.getPassword());

		// 탈퇴 후 세션 무효화
		session.invalidate();

		return new ResponseEntity<>(new DeleteUserResponseDto(), HttpStatus.OK);
	}

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

		return new ResponseEntity<>(new LogoutUserResponseDto(), HttpStatus.OK);
	}

	// 회원 다건 조회
	@GetMapping
	public ResponseEntity<List<FindUserResponseDto>> userFindAllAPI() {
		List<FindUserResponseDto> userList = userService.userFindAllService();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
}
