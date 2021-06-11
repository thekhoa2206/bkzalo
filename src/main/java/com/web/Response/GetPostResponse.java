package com.web.Response;

public class GetPostResponse {
	private String id;
	private String described;
	private String created;
	private String modified;
	private String like;
	private String comment;
	private String is_liked;

	
	
	public GetPostResponse(String id, String described, String created, String modified, String like, String comment,
			String is_liked) {
		this.id = id;
		this.described = described;
		this.created = created;
		this.modified = modified;
		this.like = like;
		this.comment = comment;
		this.is_liked = is_liked;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescribed() {
		return described;
	}
	public void setDescribed(String described) {
		this.described = described;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getLike() {
		return like;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getIs_liked() {
		return is_liked;
	}
	public void setIs_liked(String is_liked) {
		this.is_liked = is_liked;
	}
	
	
	
}
