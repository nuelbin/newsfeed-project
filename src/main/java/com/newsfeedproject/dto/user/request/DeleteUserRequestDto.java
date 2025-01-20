package com.newsfeedproject.dto.user.request;

import lombok.Getter;

@Getter
public class DeleteUserRequestDto {
	private final String password;

	public DeleteUserRequestDto(String password) {
		this.password = password;
	}
}
