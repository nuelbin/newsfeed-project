package com.newsfeedproject.common.exception.friend;

import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;

public class FriendRequestNotAuthorizedException extends BaseException {
	public FriendRequestNotAuthorizedException() {
		super(ResponseCode.FRIEND_REQUEST_NOT_AUTHORIZED);
	}
}