package com.newsfeedproject.dto.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindCategoryResponseDto {
    private String categoryName;

    public FindCategoryResponseDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
