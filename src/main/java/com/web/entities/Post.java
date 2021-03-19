package com.web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post")
//	private List<User> users = new ArrayList<User>();
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post"/* tên property product trong class ProductImages */
			, fetch = FetchType.EAGER, orphanRemoval = true)

	private List<PostImgaes> postImages = new ArrayList<PostImgaes>();
//	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post")
//	private List<Comment> comment = new ArrayList<Comment>();

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


}
