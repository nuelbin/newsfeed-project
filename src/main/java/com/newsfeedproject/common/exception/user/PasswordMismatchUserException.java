package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class PasswordMismatchUserException extends BaseException {
	public PasswordMismatchUserException() {
		super(ResponseCode.PASSWORD_MISMATCH);
	}
}
