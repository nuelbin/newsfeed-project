package com.newsfeedproject.dto.friend.response;

import java.time.LocalDateTime;

import com.newsfeedproject.common.entity.friend.FriendStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendWithUpdateResponseDto {
	private Long id;             // Friend 엔티티의 ID
	private Long userId;         // 상대방 유저의 ID
	private String userName;     // 상대방 유저의 이름
	private FriendStatus status; // 친구 상태
	private LocalDateTime updatedAt; // 업데이트일
}