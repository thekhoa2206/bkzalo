package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.common.SearchSomethings;
import com.web.entities.AjaxResponse;
import com.web.entities.Post;
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

	@RequestMapping(value = { "/seachSomethingOfPost" }, method = RequestMethod.GET)
	public ResponseEntity<AjaxResponse> see_post_user(@RequestBody String keyword, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		SearchSomethings searchSomethings = new SearchSomethings();
		searchSomethings.setKeyword(keyword);
		List<Post> postData = postService.search(searchSomethings);
		return ResponseEntity.ok(new AjaxResponse(200, "OK", postData));
	}

}
