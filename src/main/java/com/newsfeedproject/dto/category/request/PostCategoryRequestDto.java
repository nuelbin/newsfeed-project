package com.newsfeedproject.dto.category.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCategoryRequestDto {
    private String categoryName;

    public PostCategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }
}

