package com.newsfeedproject.common.exception.friend;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class FriendNotFoundException extends BaseException {
	public FriendNotFoundException() {
		super(ResponseCode.FRIEND_NOT_FOUND);
	}
}