package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.entities.Post;
import com.web.entities.User;
import com.web.repositories.PostRepo;
import com.web.services.PostService;
import com.web.services.UserService;

@RestController
public class LikeController {
	@Autowired
	PostRepo postRepo;

	@Autowired
	PostService postService;

	@Autowired
	UserService userService;

	@PostMapping(value = { "/like" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> like(@RequestParam String token, @RequestParam int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		Post post = postService.findPostById(id);
		post.getLike().add(userService.findUserByPhone(userService.getPhoneNumberFromToken(token)));
		postRepo.save(post);
		Post like = postService.findPostById(id);
		String data = Integer.toString(like.getLike().size());
		return ResponseEntity.ok(new AjaxResponse(1000, "OK", data));
	}

}
