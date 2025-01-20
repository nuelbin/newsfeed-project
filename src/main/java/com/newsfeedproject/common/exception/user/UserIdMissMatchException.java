package com.newsfeedproject.common.exception.user;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class UserIdMissMatchException extends BaseException {
	public UserIdMissMatchException() {
		super(ResponseCode.ID_MISMATCH);
	}
}
