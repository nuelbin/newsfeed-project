package com.newsfeedproject.dto.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCommentRequestDto {

	private Long userId;  // 로그인 한 유저 아이디 -> 더이상 dto에서 쓸모 없는 아이..
	private String content;
	private Long parentCommentId = 0L; // 부모 아이디 존재 검증 로직을 위해 추가

}
