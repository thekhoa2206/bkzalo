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
import com.web.entities.Post;
import com.web.entities.Response;
import com.web.repositories.PostRepo;
import com.web.services.PostService;
import com.web.services.UserService;

@RestController
public class PutPostController extends BaseController{
	@Autowired
	PostRepo postRepo;
	@Autowired
	public PostService postService;
	@Autowired
	public UserService userService;
	

//	User đăng bài
	@PostMapping(value = { "/user/{id_user}/put_post" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> Put_Post(@PathVariable("id_user") int id_user, @RequestBody Post postData,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		String content = String.valueOf(postData.getContent());
		String media = String.valueOf(postData.getMedia());
		Post post = new Post();
		post.setContent(content);
		post.setMedia(media);
		post.setUser(userService.findUserById(id_user));
		postRepo.save(post);
		return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, postData));
	}

//  Tìm bài viết của user (riêng từng bài )
	@GetMapping(value = { "/see_user_post/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> see_post_user(@PathVariable("id") int id, @RequestBody Post PostData,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		PostData = postService.findPostById(id);
		return ResponseEntity.ok(new AjaxResponse(200, "OK", PostData));
	}

//	Xem tất cả bài viết của tất cả user
	@GetMapping(value = { "/see_allPost" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> see_allPost(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		List<Post> PostData = postService.findAllPostUser();
		return ResponseEntity.ok(new AjaxResponse(200, "SUCCESSFULLY", PostData));
	}

//	Edit post -> Chưa làm được user nào xóa bài của user đấy -> thêm tbl-users.id
	@PostMapping(value = { "/update_post_info/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> update_post_info(@PathVariable("id") int id, @RequestBody Post postData,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		Post post = postService.findPostById(id);
		// Không xóa thông tin xong DB
		if (postData.getContent() != null) {
			post.setContent(postData.getContent());
		}
		if (postData.getMedia() != null) {
			post.setMedia(postData.getMedia());
		}
		if (postData.getUser() != null) {
			post.setUser(postData.getUser());
		}
		// Không xóa thông tin xong DB
		try {
			postService.savePost(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(200, "update successfully", postData));
	}

	// Xóa bài viết -> Nhưng chưa làm đc của user nào xóa bài của user đấy -> Lấy cả tbl-posts.id và tbl-user.id
	@GetMapping(value = { "/delete_user_post/user/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> delete_post_user(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		postService.deletePostById(id);
		return ResponseEntity.ok(new AjaxResponse(200, "OK"));
	}
	// Báo cáo bài viết
	@GetMapping(value = { "/report/user/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> report(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		return ResponseEntity.ok(new AjaxResponse(200, "OK"));
	}

}
