package com.newsfeedproject.dto.comment.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindAllCommentResponseDto {
	private Long id;
	private String userName;
	private String content;
	private List<CreateCommentResponseDto> replies = new ArrayList<>();  // 대댓글(답글) 목록 추가
}
