package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryResponseDto {
    private Long postId;       // 수정된 Post의 ID
    private Long categoryId;
    private String message;

    public UpdateCategoryResponseDto(Long postId, Long categoryId, String message) {
        this.postId = postId;
        this.categoryId = categoryId;
        this.message = message;
    }
}

