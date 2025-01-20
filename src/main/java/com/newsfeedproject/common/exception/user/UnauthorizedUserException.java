package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class UnauthorizedUserException extends BaseException {
	public UnauthorizedUserException() {
		super(ResponseCode.UNAUTHORIZED_USER);
	}
}
