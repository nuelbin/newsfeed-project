package com.newsfeedproject.repository.category;

import com.newsfeedproject.common.entity.category.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
