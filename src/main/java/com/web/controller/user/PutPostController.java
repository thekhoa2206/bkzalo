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
import com.web.Response.PostResponse;
import com.web.entities.Comment;
import com.web.entities.Post;
import com.web.entities.PostImages;
import com.web.entities.Response;
import com.web.repositories.PostRepo;
import com.web.services.PostService;
import com.web.services.UserService;

@RestController
public class PutPostController extends BaseController {
	@Autowired
	PostRepo postRepo;
	@Autowired
	public PostService postService;
	@Autowired
	public UserService userService;

//	User đăng bài
	@PostMapping(value = { "/add_post" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> add_post(@RequestBody Post postData, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);

		if (phone != null) {
			if (postService.countWords(postData.getContent()) <= 500) {
				if ((postData.getImage() != null && postData.getMedia() == null)
						|| (postData.getImage() == null && postData.getMedia() != null)) {
					if (postData.getImage().size() <= 4) {
						Post post = new Post();
						post.setImage(postData.getImage());
						post.setContent(postData.getContent());
						post.setMedia(postData.getMedia());
						post.setCreatedDate(java.time.LocalDateTime.now());
						post.setUser(userService.finUserByPhone(phone));
						postRepo.save(post);
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, postData));
					} else {
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1008, Response.MESSAGE_1008));
					}
				} else {
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1023, Response.MESSAGE_1023));
				}

			} else {
				return ResponseEntity.ok(new AjaxResponse(Response.CODE_1022, Response.MESSAGE_1022));
			}

		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_9998, Response.MESSAGE_9998));
		}
	}

	// Tìm bài viết của user (riêng từng bài )
	@GetMapping(value = { "/get_post/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_post(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		int comment = 0;
		List<Comment> comment1 = postService.findAllCommentByIdPost(id);
		for (Comment comment2 : comment1) {
			comment++;
		}
		if (phone != null) {
			Post PostData = postService.findPostById(id);
			return ResponseEntity.ok(new PostResponse(Response.CODE_1000, Response.MESSAGE_1000, PostData, comment));
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_9998, Response.MESSAGE_9998));
		}
	}

	// Xem tất cả bài viết của tất cả user
	@GetMapping(value = { "/get_list_posts" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_list_posts(@RequestParam("index") int index,
			@RequestParam("count") int count, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);

		int lastId = 0;
		List<Post> postData = new ArrayList<Post>();
		List<Post> data = postRepo.findAll();

		if (phone != null) {

			if (data.size() < count) {
				for (int i = index; i < data.size(); i++) {
					postData.add(data.get(i));
					System.out.println(postData.get(i));

				}
			} else {
				for (int i = index; i < count; i++) {
					postData.add(data.get(i));
					System.out.println(postData.get(i));
				}
			}
			lastId = postData.get(postData.size()-1).getId();

			return ResponseEntity.ok(new PostResponse(Response.CODE_1000, Response.MESSAGE_1000, postData, lastId));
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_9998, Response.MESSAGE_9998));
		}
	}

	// Edit post -> Chưa làm được user nào xóa bài của user đấy -> thêm tbl-users.id
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

	// Xóa bài viết -> Nhưng chưa làm đc của user nào xóa bài của user đấy -> Lấy cả
	// tbl-posts.id và tbl-user.id
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
