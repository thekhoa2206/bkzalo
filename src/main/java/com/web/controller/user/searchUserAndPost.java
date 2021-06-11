package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.Response.SearchResponse;
import com.web.entities.Response;
import com.web.repositories.PostRepo;
import com.web.services.PostService;
import com.web.services.UserService;

@RestController
public class searchUserAndPost {
	@Autowired
	PostRepo postRepo;
	@Autowired
	public PostService postService;
	@Autowired
	public UserService userService;


	@PostMapping(value = { "/search" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> search(@RequestParam String keyword, @RequestParam String token,
			@RequestParam int index, @RequestParam int count,@RequestParam int category_id, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		SearchResponse searchResponse = new SearchResponse(keyword, keyword, keyword, null);
		switch(category_id) {
		case 1:	//search post
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000,postService.search(keyword)));
		case 2:
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000,postService.searchUser(keyword)));
		case 3:
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000,postService.searchUserChat(keyword)));
		case 4:
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000));
		default:
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000));
		}
		
		
	}

}
