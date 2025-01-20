package com.newsfeedproject.common.entity.post;

import com.newsfeedproject.common.entity.BaseEntity;
import com.newsfeedproject.common.entity.user.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public Post(User user,String content) {
		this.user = user;
		this.content = content;
	}

	public void updatePost(String content) {
		this.content = content;
	}
}
