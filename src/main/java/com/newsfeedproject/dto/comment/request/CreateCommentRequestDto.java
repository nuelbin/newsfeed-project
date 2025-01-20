package com.newsfeedproject.dto.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCommentRequestDto {

	private String content;
	private Long parentCommentId = 0L; // 부모 아이디 존재 검증 로직을 위해 추가

}
