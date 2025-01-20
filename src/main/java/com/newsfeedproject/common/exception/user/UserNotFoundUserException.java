package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class UserNotFoundUserException extends BaseException {
	public UserNotFoundUserException() {
		super(ResponseCode.USER_NOT_FOUND);
	}
}
