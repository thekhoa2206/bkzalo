package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.common.SearchSomethings;
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

//	Tìm kiếm keywords theo tên post
	@RequestMapping(value = { "/seachSomethingOfPost" }, method = RequestMethod.GET)
	public ResponseEntity<AjaxResponse> search_post(@RequestBody SearchSomethings keyword, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		System.out.print(keyword.getKeyword());
		return ResponseEntity.ok(new AjaxResponse(200, "OK", postService.search(keyword)));
	}

//	Tìm kiếm theo tên user
	@RequestMapping(value = { "/seachSomethingOfUser" }, method = RequestMethod.GET)
	public ResponseEntity<AjaxResponse> search_user(@RequestBody SearchSomethings keyword, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		System.out.print(keyword.getKeyword());
		return ResponseEntity.ok(new AjaxResponse(200, "OK", postService.searchUser(keyword)));
	}

}
