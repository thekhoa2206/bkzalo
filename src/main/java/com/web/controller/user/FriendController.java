package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.common.SearchSomethings;
import com.web.entities.AjaxResponse;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

@RestController
public class FriendController {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	UserService userService;

	// Lấy thông tin FriendRequest
	@RequestMapping(value = { "/get_friend_request_info" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> search_user(@RequestBody SearchSomethings keyword, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		System.out.print(keyword.getKeyword());
		return ResponseEntity.ok(new AjaxResponse(200, "Success!", userService.findFriendById(keyword)));
	}

	// Xem danh sách bạn bè
	@PostMapping(value = { "/get_friend_info/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_friend_info(@PathVariable("id") int id, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		return ResponseEntity.ok(new AjaxResponse(200, "Success!", userService.findFriendInfo(id)));
	}
	//Blocks
	
	
	
	
	
	//Chấp nhận yêu cầu kết bạn
	
	
	
	
	
	
	//bỏ block
	
	
}
