package com.newsfeedproject.dto.friend.response;

import com.newsfeedproject.common.entity.friend.Friend;
import com.newsfeedproject.common.entity.friend.FriendStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResponseDto {
	private Long id;             // Friend 엔티티의 ID
	private Long userId;         // 상대방 유저의 ID
	private String userName;     // 상대방 유저의 이름
	private FriendStatus status; // 친구 상태

	public FriendResponseDto(Friend friend, Long userId, String userName) {
		this.id = friend.getId();
		this.userId = friend.getToUser().getId();
		this.userName = userName;
		this.status = friend.getStatus();

	}
}