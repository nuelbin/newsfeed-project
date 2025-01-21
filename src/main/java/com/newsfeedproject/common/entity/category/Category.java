package com.newsfeedproject.common.entity.category;

import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategory> postCategoryList = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
