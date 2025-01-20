package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class EmailAlreadyExistsUserException extends BaseException {
	public EmailAlreadyExistsUserException() {
		super(ResponseCode.EMAIL_ALREADY_EXISTS);
	}
}
