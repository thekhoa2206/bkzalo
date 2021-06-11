package com.web.Response;

import com.web.entities.Author;

public class SearchResponse {
	private String id_message;
	private String id_conversation;
	private String found;
	private Author author;

	public SearchResponse(String id_message, String id_conversation, String found, Author author) {
		super();
		this.id_message = id_message;
		this.id_conversation = id_conversation;
		this.found = found;
		this.author = author;
	}

	public String getId_message() {
		return id_message;
	}

	public void setId_message(String id_message) {
		this.id_message = id_message;
	}

	public String getId_conversation() {
		return id_conversation;
	}

	public void setId_conversation(String id_conversation) {
		this.id_conversation = id_conversation;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
