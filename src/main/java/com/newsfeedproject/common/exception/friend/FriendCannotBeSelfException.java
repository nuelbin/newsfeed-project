package com.newsfeedproject.common.exception.friend;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class FriendCannotBeSelfException extends BaseException {
	public FriendCannotBeSelfException() {
		super(ResponseCode.USER_CANNOT_FRIEND_SELF);
	}
}