package com.newsfeedproject.dto.category.response;

import com.newsfeedproject.dto.post.response.FindPostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostCategoryResponseDto {
    private String categoryName;
    private List<FindPostResponseDto> posts;

    public PostCategoryResponseDto(String categoryName, List<FindPostResponseDto> posts) {
        this.categoryName = categoryName;
        this.posts = posts;
    }
}
