package com.web.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_users")
public class User extends BaseEntity {

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "phone", length = 45, nullable = false)
	private String phone;

	@Column(name = "avatar", length = 100, nullable = false)
	private String avatar;
	
	// 1 user -> N posts
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" /* tên property user trong class posts */
			, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<Post>();
	
	// 1 user -> N commments
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" /* tên property user trong class comment */
				, fetch = FetchType.LAZY)
		private List<Comment> comment = new ArrayList<Comment>();
		
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

}
