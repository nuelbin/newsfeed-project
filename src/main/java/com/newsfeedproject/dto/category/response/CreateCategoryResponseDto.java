package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCategoryResponseDto {
    private String message;
    private String categoryName;

    public CreateCategoryResponseDto(String message) {
        this.message = message;
    }

    public CreateCategoryResponseDto(String message, String categoryName) {
        this.message = message;
        this.categoryName = categoryName;
    }
}
