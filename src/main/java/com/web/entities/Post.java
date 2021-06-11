package com.web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_posts")
public class Post extends BaseEntity {
	@Column(name = "content", length = 1000, nullable = true)
	private String content;

	@Column(name = "media", length = 100, nullable = false)
	private String media;

	@Column(name = "create_date", nullable = true)
	private LocalDateTime createdDate;

	@Column(name = "can_comment", nullable = false)
	private boolean canComment;

	@Column(name = "state", nullable = false)
	private boolean state;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_likes_posts", joinColumns = @JoinColumn(name = "id_users"), inverseJoinColumns = @JoinColumn(name = "id_post"))
	private List<User> like = new ArrayList<User>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post"/* tên property product trong class ProductImages */
			, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PostImages> image = new ArrayList<PostImages>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post" /* tên property post trong class report */
			, fetch = FetchType.LAZY)
	private List<Report> report = new ArrayList<Report>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "post")
	private List<Comment> comment = new ArrayList<Comment>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getLike() {
		return like;
	}

	public void setLike(List<User> like) {
		this.like = like;
	}

	public List<PostImages> getImage() {
		return image;
	}

	public void setImage(List<PostImages> image) {
		this.image = image;
	}

	public boolean getCanComment() {
		return canComment;
	}

	public void setCanComment(boolean canComment) {
		this.canComment = canComment;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
