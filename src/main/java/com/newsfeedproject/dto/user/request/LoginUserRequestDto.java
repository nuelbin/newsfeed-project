package com.newsfeedproject.dto.user.request;

import lombok.Getter;

@Getter
public class LoginUserRequestDto {
	private final String email;
	private final String password;

	public LoginUserRequestDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
