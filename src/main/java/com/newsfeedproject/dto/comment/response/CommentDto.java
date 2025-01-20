package com.newsfeedproject.dto.comment.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.newsfeedproject.common.entity.comment.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDto {
	private Long id;
	private Long parentId;
	private String content;
	private LocalDateTime createdAt;

	public static List<CommentDto> convertDto(List<Comment> comments) {
		return comments.stream().
			map(
				comment -> new CommentDto(
					comment.getId(),
					comment.getParent().getId(),
					comment.getContent(),
					comment.getCreatedAt()))
			.collect(Collectors.toList());
	}

}
