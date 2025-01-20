package com.newsfeedproject.common.exception.post;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class UserNotFoundException extends BaseException {
	public UserNotFoundException() {
		super(ResponseCode.USER_NOT_FOUND);
	}
}
