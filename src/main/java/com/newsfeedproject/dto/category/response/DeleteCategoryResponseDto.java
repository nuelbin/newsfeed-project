package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteCategoryResponseDto {
    private String message;

    public DeleteCategoryResponseDto(String message) {
        this.message = message;
    }
}

