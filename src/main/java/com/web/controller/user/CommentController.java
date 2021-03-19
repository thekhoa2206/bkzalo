package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.entities.AjaxResponse;
import com.web.entities.Comment;
import com.web.entities.Post;
import com.web.entities.User;
import com.web.repositories.CommentRepo;
import com.web.repositories.PostRepo;
import com.web.services.CommentService;
import com.web.services.UserService;

@RestController
public class CommentController extends BaseController {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	PostRepo postRepo;

	@Autowired
	CommentService commentService;

	@Autowired
	UserService userService;

	@GetMapping(value = { "/get_comment/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_comment(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		List<Comment> comment = commentService.findPostCommentById(id);
		return ResponseEntity.ok(new AjaxResponse(200, "Success!", comment));
	}

	@PostMapping(value = { "/post/{id}/set_comment" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_comment(@PathVariable("id") int id, @RequestBody Comment data,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		User user = userService.findUserById(1);
		data.setUser(user);
		List<Post> post = userService.findPostById(id);
		data.setPost(post);
		try {
			commentService.saveComment(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(200, "Comment Success!", data));
	}

	@GetMapping(value = { "/post/{id_post}/del_comment/{id_comment}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> del_comment(@PathVariable("id_post") int idPost,
			@PathVariable("id_comment") int idComment, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		commentService.deleteComment(idComment);
		Comment data = null;
		return ResponseEntity.ok(new AjaxResponse(200, "Delete success!", data));
	}

	@PostMapping(value = { "/post/{id_post}/edit_comment/{id_comment}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> edit_comment(@PathVariable("id_post") int idPost,
			@PathVariable("id_comment") int idComment, @RequestBody Comment data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		data.setUser(userService.findUserById(1));
		data.setPost(userService.findPostById(idPost));
		data.setId(idComment);
		try {
			commentService.saveComment(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(200, "Edit Success!", data));
	}
}
