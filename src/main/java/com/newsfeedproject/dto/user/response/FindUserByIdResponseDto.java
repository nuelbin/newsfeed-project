package com.newsfeedproject.dto.user.response;

import com.newsfeedproject.common.entity.user.User;

import lombok.Getter;

@Getter
public class FindUserByIdResponseDto {
	private String userName;

	public FindUserByIdResponseDto(User user) {
		this.userName = user.getUserName();
	}
}
