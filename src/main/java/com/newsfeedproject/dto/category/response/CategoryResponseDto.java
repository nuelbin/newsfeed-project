package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String message;
    private String categoryName;

    public CategoryResponseDto(String message) {
        this.message = message;
    }

    public CategoryResponseDto(String message, String categoryName) {
        this.message = message;
        this.categoryName = categoryName;
    }
}
