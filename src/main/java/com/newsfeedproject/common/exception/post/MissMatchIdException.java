package com.newsfeedproject.common.exception.post;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class MissMatchIdException extends BaseException {
	public MissMatchIdException() {
		super(ResponseCode.ID_MISMATCH);
	}
}
