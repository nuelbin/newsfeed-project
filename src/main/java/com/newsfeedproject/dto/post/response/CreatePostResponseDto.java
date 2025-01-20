package com.newsfeedproject.dto.post.response;

import java.time.LocalDateTime;

import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;

import lombok.Getter;

@Getter
public class CreatePostResponseDto {
	private String userName;
	private String message;
	private String content;
	private LocalDateTime createdAt;

	public CreatePostResponseDto(String userName, String message, Post savePost) {
		this.userName = userName;
		this.message = message;
		this.content = savePost.getContent();
		this.createdAt = savePost.getCreatedAt();
	}

}
