package com.web.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	@Transient
	private String token;

	// 1 user -> N posts
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" /* tên property user trong class posts */
			, fetch = FetchType.LAZY)
	private List<Post> post = new ArrayList<Post>();

	// 1 user -> N commments
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" /* tên property user trong class comment */
			, fetch = FetchType.LAZY)
	private List<Comment> comment = new ArrayList<Comment>();

	// 1 user -> N commments
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userSender" /* tên property user trong class comment */
			, fetch = FetchType.LAZY)
	private List<Chat> chatSender = new ArrayList<Chat>();

	// 1 user -> N commments
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userReceiver" /* tên property user trong class comment */
			, fetch = FetchType.LAZY)
	private List<Chat> chatReceiver = new ArrayList<Chat>();

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "like")
	private List<Post> like = new ArrayList<Post>();

	// 1 user -> N friends
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userAId" /* tên property user trong class friend */
			, fetch = FetchType.LAZY)
	private List<Friend> friendA = new ArrayList<Friend>();

	// 1 user -> N friends
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userBId" /* tên property user trong class friend */
			, fetch = FetchType.LAZY)
	private List<Friend> friendB = new ArrayList<Friend>();

	// 1 user -> Block many friends
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_user_block" /* tên property user trong class block */
			, fetch = FetchType.LAZY)
	private List<Block> userBlock = new ArrayList<Block>();

	// 1 user -> Block many friends
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_block_user" /* tên property user trong class block */
			, fetch = FetchType.LAZY)
	private List<Block> blockUser = new ArrayList<Block>();

	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_role_user", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
	private List<Roles> roles = new ArrayList<Roles>();
	
	
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

}
