package com.newsfeedproject.dto.user.response;

import lombok.Getter;

@Getter
public class DeleteUserResponseDto {
	private final String message;

	public DeleteUserResponseDto() {
		this.message = "회원 탈퇴 처리되었습니다.";
	}
}
