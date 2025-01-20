package com.newsfeedproject.dto.user.request;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {

	private final String email;
	private final String userName;
	private final String password;
	private final String reEnterPassword;

	public CreateUserRequestDto(String email, String userName, String password, String reEnterPassword) {
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.reEnterPassword = reEnterPassword;
	}
}