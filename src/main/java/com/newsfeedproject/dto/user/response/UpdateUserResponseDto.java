package com.newsfeedproject.dto.user.response;

import lombok.Getter;

@Getter
public class UpdateUserResponseDto {
	public String message;

	public UpdateUserResponseDto() {
		this.message = "회원 정보가 수정 되었습니다.";
	}
}
