package com.newsfeedproject.dto.category.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryRequestDto {
    private Long categoryId;

    public UpdateCategoryRequestDto(Long categoryId) {
        this.categoryId = categoryId;
    }
}

