package com.newsfeedproject.common.exception.friend;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class FriendCannotBeDeletedException extends BaseException {
	public FriendCannotBeDeletedException() {
		super(ResponseCode.FRIEND_CANNOT_BE_DELETED);
	}
}