package com.newsfeedproject.common.exception.comment;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class UnauthorizedAccessException extends BaseException {
	public UnauthorizedAccessException() {
		super(ResponseCode.UNAUTHORIZED_NOT_ACCESS);
	}
}
