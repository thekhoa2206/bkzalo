package com.web.Response;

public class PostResponse extends AjaxResponse {

	private int comment;
	
	private int lastId;

	public PostResponse(int code, String message, Object data, int comment) {
		super(code, message, data);
		this.comment = comment;

	}
	
	public PostResponse(int code, String message, Object data, int comment, int lastId) {
		super(code, message, data);
		this.comment = comment;
		this.lastId = lastId;

	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

}
