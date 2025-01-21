package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String message;

    public CategoryResponseDto(String message) {
        this.message = message;
    }
}
