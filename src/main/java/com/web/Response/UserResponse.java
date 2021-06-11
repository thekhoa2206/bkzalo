package com.web.Response;

public class UserResponse {
	private String name;
	private String avatar;
	
	
	public UserResponse(String name, String avatar) {
		this.name = name;
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
