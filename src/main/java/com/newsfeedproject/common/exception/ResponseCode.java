package com.newsfeedproject.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseCode {

	PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다. 다시 입력해주세요."),
	URL_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 경로입니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
	USER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 탈퇴 처리 된 회원입니다."),
	USER_DELETE(HttpStatus.BAD_REQUEST, "탈퇴한 회원은 로그인 할 수 없습니다."),
	PASSWORD_SAME_AS_BEFORE(HttpStatus.BAD_REQUEST, "바꾸려는 비밀번호가 이전과 동일하거나, 입력한 비밀번호가 서로 다릅니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 아이디를 찾을 수 없습니다. 다시 확인해주세요."),
	ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),

	COMMENT_NOT_FOUND(HttpStatus.NO_CONTENT, "부모 댓글이 존재하지 않습니다."),
	POST_NOT_FOUND(HttpStatus.NO_CONTENT, "게시물이 존재하지 않습니다."),
	POST_IS_DELETE(HttpStatus.BAD_REQUEST, "삭제된 게시글은 조회/삭제 할 수 없습니다."),
	UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 회원입니다. 로그인 후 이용해주세요.");

	private final HttpStatus status;
	private final String message;

	ResponseCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
