package com.web.Response;

public class LoginResponse {
	private String id;
	private String username;
	private String token;
	private String avatar;
	
	
	public LoginResponse(String id, String username, String token, String avatar) {
		this.id = id;
		this.username = username;
		this.token = token;
		this.avatar = avatar;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
}
