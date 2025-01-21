package com.newsfeedproject.common.entity.post;

import com.newsfeedproject.common.entity.BaseEntity;
import com.newsfeedproject.common.entity.category.PostCategory;
import com.newsfeedproject.common.entity.user.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostCategory> postCategoryList = new ArrayList<>();

	public Post(String content) {
    
	public Post(User user,String content) {
		this.user = user;
		this.content = content;
	}

	public void updatePost(String content) {
		this.content = content;
	}
}
