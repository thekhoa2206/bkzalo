package com.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_response")
public class AjaxResponse extends BaseEntity {

	@Column(name = "code", nullable = false)
	private int code;
	
	@Column(name = "message", length = 100, nullable = false)
	private String message;
	
	@Column(name = "note", length = 500, nullable = false)
	private String note;
	
	@Transient
	private Object data;

	public AjaxResponse(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public AjaxResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}