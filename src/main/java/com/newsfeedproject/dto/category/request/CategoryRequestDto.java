package com.newsfeedproject.dto.category.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {
    private String categoryName;

    public CategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
