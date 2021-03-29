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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.common.SearchSomethings;
import com.web.entities.AjaxResponse;
import com.web.entities.Block;
import com.web.entities.Friend;
import com.web.repositories.BlockRepo;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

@RestController
public class FriendController {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	UserService userService;
	@Autowired
	BlockRepo blockRepo;

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

	// Set_block_user
	@PostMapping(value = { "/block" }, produces = "application/json")

	public ResponseEntity<AjaxResponse> block_user(@RequestParam("id_user_block") int id_user_block,
			@RequestParam("id_block_user") int id_block_user, final ModelMap model, Block blockUser,
			final HttpServletRequest request, final HttpServletResponse response) {
		blockUser.setId_block_user(userService.findUserById(id_user_block));
		blockUser.setId_block_user(userService.findUserById(id_block_user));
		blockRepo.save(blockUser);
		return ResponseEntity.ok(new AjaxResponse(200, "Block Successfully!", blockUser));
	}

	// Gửi yêu cầu kết bạn
	@RequestMapping(value = { "/set_request_friend" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> set_request_friend(@RequestParam Integer idA,@RequestParam Integer idB,Friend friendData, final ModelMap model,
														   final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		friendData.setUserAId(userService.findUserById(idA));
		friendData.setUserBId(userService.findUserById(idB));
		friendData.setIsAccept(false);
		userService.saveFriendRequest(friendData);
		return ResponseEntity.ok(new AjaxResponse(200, "Success!",friendData));
	}


	// Chấp nhận yêu cầu kết bạn

	// bỏ block

}
