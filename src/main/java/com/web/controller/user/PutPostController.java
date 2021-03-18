package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.entities.AjaxResponse;
import com.web.entities.Post;
import com.web.repositories.PostRepo;
import com.web.services.PostService;
import com.web.services.UserService;

@RestController
public class PutPostController {
	@Autowired
	PostRepo postRepo;
	@Autowired
	public PostService postService;
	@Autowired
	public UserService userService;

	@RequestMapping(value = { "/user/{id_user}/put_post" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> Put_Post(@PathVariable("id_user") int id_user, @RequestBody Post postData,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		String content = String.valueOf(postData.getContent());
		String media = String.valueOf(postData.getMedia());
		Post post = new Post();
		post.setContent(content);
		post.setMedia(media);
		post.setUser(userService.findUserById(id_user));
		postRepo.save(post);
		return ResponseEntity.ok(new AjaxResponse(200, "Post Status successful", postData));
	}

	@RequestMapping(value = { "/see_user_post/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<AjaxResponse> see_post_user(@PathVariable("id") int id, @RequestBody Post PostData,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		PostData = postService.findPostById(id);
		return ResponseEntity.ok(new AjaxResponse(200, "OK", PostData));
	}

	@RequestMapping(value = { "/see_allPost" }, method = RequestMethod.GET)
	public ResponseEntity<AjaxResponse> see_allPost(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		List<Post> PostData = postService.findAllPostUser();
		return ResponseEntity.ok(new AjaxResponse(200, "SUCCESSFULLY", PostData));
	}

	@RequestMapping(value = { "/delete_user_post/user/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<AjaxResponse> delete_post_user(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		postService.deletePostById(id);
		return ResponseEntity.ok(new AjaxResponse(200, "OK"));
	}

}
