package com.web.controller.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AddPostResponse;
import com.web.Response.AjaxResponse;
import com.web.Response.PostResponse;
import com.web.entities.Comment;
import com.web.entities.Post;
import com.web.entities.PostImages;
import com.web.entities.Report;
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
	public ResponseEntity<AjaxResponse> add_post(@RequestParam String token, @RequestParam String image,
			@RequestParam String video, @RequestParam String described, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		String phone = userService.getPhoneNumberFromToken(token);
		Post data = new Post();
		data.setContent(described);
		data.setUser(userService.findUserByPhone(userService.getPhoneNumberFromToken(token)));
		List<PostImages> images = new ArrayList<PostImages>();
		PostImages image0 = new PostImages();
		image0.setPath(image);
		images.add(0, image0);
		data.setImage(images);
		data.setMedia(video);

		if (phone != null) {
			if (postService.countWords(data.getContent()) <= 500) {

				if (data.getImage().isEmpty() == false && data.getMedia().length() != 0) {
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1023, Response.MESSAGE_1023));
				} else {
					if (data.getImage().size() <= 4) {
						Post post = new Post();
						post.setImage(data.getImage());
						post.setContent(data.getContent());
						post.setMedia(data.getMedia());
						post.setCreatedDate(java.time.LocalDateTime.now());
						post.setUser(userService.findUserByPhone(phone));
						postRepo.save(post);

						String url = "http://localhost:8080/add_post/" + Integer.toString(post.getId());

						AddPostResponse addPost = new AddPostResponse(Integer.toString(post.getId()), url);
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, addPost));
					} else {
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1008, Response.MESSAGE_1008));
					}

				}

			} else {
				return ResponseEntity.ok(new AjaxResponse(Response.CODE_1022, Response.MESSAGE_1022));
			}

		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_9998, Response.MESSAGE_9998));
		}
	}

	// Tìm bài viết của user (riêng từng bài )
	@GetMapping(value = { "/get_post" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_post(@RequestParam String token, @RequestParam int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
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
	public ResponseEntity<AjaxResponse> get_list_posts(@RequestParam String token, @RequestParam String last_id,
			@RequestParam String index, @RequestParam String count, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		String phone = userService.getPhoneNumberFromToken(token);

		int lastId = 0;
		List<Post> postData = new ArrayList<Post>();
		List<Post> data = postRepo.findAll();
		int index1 = Integer.parseInt(index);
		int count1 = Integer.parseInt(count);
		if (phone != null) {
			int last = index1 + count1;
			if (data.size() < last) {
				for (int i = index1; i < data.size(); i++) {
					postData.add(data.get(i));
				}
			} else {
				for (int i = index1; i < last; i++) {
					postData.add(data.get(i));
				}
			}
			lastId = postData.get(postData.size() - 1).getId();

			return ResponseEntity.ok(new PostResponse(Response.CODE_1000, Response.MESSAGE_1000, postData, lastId));
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_9998, Response.MESSAGE_9998));
		}
	}

	// Edit post -> Chưa làm được user nào xóa bài của user đấy -> thêm tbl-users.id
	@PostMapping(value = { "/edit_post" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> edit_post(@RequestParam String token, @RequestParam String id,
			@RequestParam String described, @RequestParam String image, @RequestParam String image_del,
			@RequestParam String image_sort, @RequestParam String video, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		Post post = postService.findPostById(Integer.parseInt(id));
		Post postData = new Post();
		postData.setId(Integer.parseInt(id));
		postData.setContent(described);
		postData.setUser(userService.findUserByPhone(userService.getPhoneNumberFromToken(token)));
		List<PostImages> images = new ArrayList<PostImages>();
		PostImages image0 = new PostImages();
		image0.setPath(image);
		images.add(0, image0);
		postData.setImage(images);
		postData.setMedia(video);

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
		post.setCreatedDate(java.time.LocalDateTime.now());
		// Không xóa thông tin xong DB
		try {
			postService.savePost(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000));
	}

	// tbl-posts.id và tbl-user.id
	@PostMapping(value = { "/delete_post" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> delete_post(@RequestParam String token, @RequestParam int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		if(postService.findPostbyIdAndUserId(id, userService.findUserByPhone(userService.getPhoneNumberFromToken(token)).getId()) != null) {
			postService.deletePostById(id,userService.findUserByPhone(userService.getPhoneNumberFromToken(token)).getId());
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000));
		}
		return ResponseEntity.ok(new AjaxResponse(Response.CODE_1025, Response.MESSAGE_1025));
	}

	// Báo cáo bài viết
	@PostMapping(value = { "/report" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> report(@RequestParam String token, @RequestParam int id,
			@RequestParam String subject, @RequestParam String details, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		if (postService.findPostById(id).getState() == true) {
			Report report = new Report();
			report.setUser(userService.findUserByPhone(userService.getPhoneNumberFromToken(token)));
			report.setPost(postService.findPostById(id));
			report.setDetails(details);
			report.setSubject(subject);
			try {
				postService.saveReport(report);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000));
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1024, Response.MESSAGE_1024));
		}

	}

}
