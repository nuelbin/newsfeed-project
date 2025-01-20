package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class EmailNotFoundUserException extends BaseException {
	public EmailNotFoundUserException() {
		super(ResponseCode.EMAIL_NOT_FOUND);
	}
}
