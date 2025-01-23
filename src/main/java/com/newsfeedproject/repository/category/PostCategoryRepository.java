package com.newsfeedproject.repository.category;

import com.newsfeedproject.common.entity.category.Category;
import com.newsfeedproject.common.entity.category.PostCategory;
import com.newsfeedproject.common.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findByCategory(Category category);
    List<PostCategory> findByPost(Post post);
}
