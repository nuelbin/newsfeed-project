package com.newsfeedproject.dto.user.request;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

	public String userName;
	public String password;
	public String reEnterPassword;

}
