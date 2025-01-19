package com.newsfeedproject.dto.user.response;

import lombok.Getter;

@Getter
public class LogoutUserResponseDto {
	private final String message;

	public LogoutUserResponseDto() {
		this.message = "로그아웃되었습니다.";
	}
}
