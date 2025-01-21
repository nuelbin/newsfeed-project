package com.newsfeedproject.dto.comment.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindAllReplyCommentResponseDto {
	private Long id;
	private Long parentId;
	private String content;
	private List<CommentDto> replies = new ArrayList<>();  // 대댓글(답글) 목록 추가
	private LocalDateTime createdAt;
}
