package com.newsfeedproject.dto.user.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class FindUserResponseDto {

	private final String userName;
	private final LocalDateTime createdAt;

	public FindUserResponseDto(String userName, LocalDateTime createdAt) {
		this.userName = userName;
		this.createdAt = createdAt;
	}
}
