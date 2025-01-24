package com.newsfeedproject.repository.category;

import com.newsfeedproject.common.entity.category.Category;
import com.newsfeedproject.common.entity.category.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findByCategory(Category category);
    Optional<PostCategory> findByPostId(Long postId);
}
