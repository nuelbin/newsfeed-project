package com.newsfeedproject.dto.comment.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCommentResponseDto {

	private Long id;
	private Long parentId;
	private String userName;
	private String content;
	private List<CommentDto> replies = new ArrayList<>();  // 대댓글(답글) 목록 추가
	private LocalDateTime createdAt;

	public CreateCommentResponseDto(Long id, Long parentId, String userName, String content, LocalDateTime createdAt) {
		this.id = id;
		this.parentId = parentId;
		this.userName = userName;
		this.content = content;
		this.createdAt = createdAt;
	}
}

