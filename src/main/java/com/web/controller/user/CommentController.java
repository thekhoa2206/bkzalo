package com.web.controller.user;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
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

	@PostMapping(value = { "/get_comment" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_comment(@RequestParam String id, @RequestParam String index,
			@RequestParam String count, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		List<Comment> comment = commentService.findPostCommentById(Integer.parseInt(id));
		List<Comment> commentData = new ArrayList<Comment>();

		int indexI = Integer.parseInt(index);
		int countI = Integer.parseInt(count);
		int last = indexI + countI;

		if (comment.size() < last) {
			for (int i = indexI; i < comment.size(); i++) {
				commentData.add(comment.get(i));
			}
		} else {
			for (int i = indexI; i < last; i++) {
				commentData.add(comment.get(i));
			}
		}
		return ResponseEntity.ok(new AjaxResponse(1000, "OK", commentData));
	}

	@PostMapping(value = { "/set_comment" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_comment(@RequestParam String id, @RequestParam String token,
			@RequestParam String index, @RequestParam String count, @RequestParam String comment, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		User user = userService.findUserByPhone(userService.getPhoneNumberFromToken(token));
		Comment data = new Comment();
		data.setUser(user);

		data.setContent(comment);
		data.setCreatedDate(java.time.LocalDateTime.now());
		List<Post> post = userService.findPostById(Integer.parseInt(id));
		data.setPost(post);
		try {
			commentService.saveComment(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(1000, "OK", data));
	}

	@PostMapping(value = { "/del_comment" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> del_comment(@RequestParam String token, @RequestParam String id,
			@RequestParam String id_com, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		commentService.deleteComment(Integer.parseInt(id_com));

		return ResponseEntity.ok(new AjaxResponse(1000, "OK"));
	}

	@PostMapping(value = { "/edit_comment" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> edit_comment(@RequestParam String token, @RequestParam String id,
			@RequestParam String id_com, @RequestParam String comment, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		Comment data = new Comment();
		data.setUser(userService.findUserByPhone(userService.getPhoneNumberFromToken(token)));
		data.setPost(userService.findPostById(Integer.parseInt(id)));
		data.setId(Integer.parseInt(id_com));
		data.setContent(comment);
		try {
			commentService.saveComment(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(1000, "OK"));
	}
}
