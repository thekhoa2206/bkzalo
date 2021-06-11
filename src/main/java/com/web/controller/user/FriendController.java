package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.Response.*;
import com.web.entities.*;
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
import com.web.repositories.BlockRepo;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FriendController {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	UserService userService;
	@Autowired
	BlockRepo blockRepo;

	// Lấy thông tin FriendRequest
	@RequestMapping(value = { "/get_requested_friend" }, method = RequestMethod.POST)
	public ResponseEntity<GetListFriendRequestResponse> get_friend_request_info(@RequestParam Integer user_id,@RequestParam("index") int index,
																@RequestParam("count") int count, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		int id = userService.findUserByPhone(phone).getId();
		GetListFriendRequestResponse getListFriendRequestResponse = new GetListFriendRequestResponse();
		ListFriendRequestResponse listFriendRequestResponse = new ListFriendRequestResponse();
		List<UserResponse> userResponses = new ArrayList<>();
		if(user_id != null){
			User user = userService.findUserById(id);
			if(user.getRoles().get(0).getId()!=1) {           //Nếu không phải admin
				if (user_id.compareTo(id) != 0) { //Truyền id của người khác
					userResponses = userService.count(userService.findFriendRequestByIdB(id).getData().getFriends(),index,count);
//					getListFriendRequestResponse.setCode(Response.CODE_1004);
//					getListFriendRequestResponse.setMessage(Response.MESSAGE_1004);
//					getListFriendRequestResponse.setData(null);
//					return ResponseEntity.ok(getListFriendRequestResponse);
				}else{
					userResponses = userService.count(userService.findFriendRequestByIdB(user_id).getData().getFriends(),index,count);
				}
			}else{
				userResponses = userService.count(userService.findFriendRequestByIdB(user_id).getData().getFriends(),index,count);
			}
		}else{
			userResponses = userService.count(userService.findFriendRequestByIdB(id).getData().getFriends(),index,count);
		}
		listFriendRequestResponse.setFriends(userResponses);
		getListFriendRequestResponse.setData(listFriendRequestResponse);
		getListFriendRequestResponse.setCode(Response.CODE_1000);
		getListFriendRequestResponse.setMessage(Response.MESSAGE_1000);
		return ResponseEntity.ok(getListFriendRequestResponse);





		/*if(user_id==null){    //Bỏ trống id
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, userService.findFriendRequestByIdB(id)));
		}else {							//Truyền vào id
			User user = userService.findUserById(id);
			if(user.getRoles().get(0).getId()!=1){           //Nếu không phải admin
				if (user_id.compareTo(id)!=0) { //Truyền id của người khác
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1004, Response.MESSAGE_1004));
				}else {
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, userService.findFriendRequestByIdB(user_id)));
				}
			}else{									//Nếu là admin

				return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, userService.findFriendRequestByIdB(user_id)));
			}
		}*/

	}

	// Xem danh sách bạn bè
	@PostMapping(value = { "/get_user_friends" }, produces = "application/json")
	public ResponseEntity<GetListFriendResponse> get_friend_info(@RequestParam Integer user_id,@RequestParam("index") int index,
														@RequestParam("count") int count, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		int id = userService.findUserByPhone(phone).getId();
		GetListFriendResponse getListFriendResponse = new GetListFriendResponse();
		ListFriendsResponse listFriendsResponse = new ListFriendsResponse();
		List<UserResponse> userResponses = new ArrayList<>();
		if(user_id != null){
			User user = userService.findUserById(id);
			if(user.getRoles().get(0).getId()!=1) {           //Nếu không phải admin
				if (user_id.compareTo(id) != 0) { //Truyền id của người khác
					userResponses = userService.count(userService.findFriendInfo(id).getData().getFriends(),index,count);
//					getListFriendResponse.setCode(Response.CODE_1004);
//					getListFriendResponse.setMessage(Response.MESSAGE_1004);
//					getListFriendResponse.setData(null);
//					return ResponseEntity.ok(getListFriendResponse);
				}else{
					userResponses = userService.count(userService.findFriendInfo(user_id).getData().getFriends(),index,count);
				}
			}else{
				userResponses = userService.count(userService.findFriendInfo(user_id).getData().getFriends(),index,count);
			}
		}else{
			userResponses = userService.count(userService.findFriendInfo(id).getData().getFriends(),index,count);
		}
		listFriendsResponse.setFriends(userResponses);
		listFriendsResponse.setTotal(userResponses.size());
		getListFriendResponse.setData(listFriendsResponse);
		getListFriendResponse.setCode(Response.CODE_1000);
		getListFriendResponse.setMessage(Response.MESSAGE_1000);
		return ResponseEntity.ok(getListFriendResponse);

		/*if(user_id==null){    //Bỏ trống id
			userResponses = userService.count(userService.findFriendInfo(id).getData().getFriends(),index,count);
		}else {							//Truyền vào id
			User user = userService.findUserById(id);
			if(user.getRoles().get(0).getId()!=1){           //Nếu không phải admin
				if (user_id.compareTo(id)!=0) { //Truyền id của người khác
					getListFriendResponse.setCode(Response.CODE_1004);
					getListFriendResponse.setMessage(Response.MESSAGE_1004);
					getListFriendResponse.setData(null);
					return ResponseEntity.ok(getListFriendResponse);
				}else {
					userResponses = userService.count(userService.findFriendInfo(id).getData().getFriends(),index,count);
				}
			}else{									//Nếu là admin
				userResponses = userService.count(userService.findFriendInfo(id).getData().getFriends(),index,count);
			}

		}*/

	}

	// Set_block_user
	@PostMapping(value = { "/block" }, produces = "application/json")

	public ResponseEntity<AjaxResponse> block_user(@RequestParam("id_user_block") int id_user_block,
			@RequestParam("id_block_user") int id_block_user, final ModelMap model, Block blockUser,
			final HttpServletRequest request, final HttpServletResponse response) {
		blockUser.setId_block_user(userService.findUserById(id_user_block));
		blockUser.setId_block_user(userService.findUserById(id_block_user));
		blockRepo.save(blockUser);
		return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, blockUser));
	}

	// Gửi yêu cầu kết bạn
	@RequestMapping(value = { "/set_request_friend" }, method = RequestMethod.POST)
	public ResponseEntity<GetRequestFriendResponse> set_request_friend(@RequestParam int user_id,Friend friendData, final ModelMap model,
														   final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		int id = userService.findUserByPhone(phone).getId();
		Long ts = System.currentTimeMillis()/1000;
		GetRequestFriendResponse getRequestFriendResponse = new GetRequestFriendResponse();
		RequestFriendResponse requestFriendResponse = new RequestFriendResponse();

		if(userService.findFriendRequestByIdCheck(id,user_id)==null && id!=user_id ){
			friendData.setUserAId(userService.findUserById(id));
			friendData.setUserBId(userService.findUserById(user_id));
			friendData.setIsAccept(false);
			friendData.setCreated(Long.toString(ts));
			userService.saveFriendRequest(friendData);


			requestFriendResponse.setRequested_friends(userService.findFriendRequestByIdA(id).size());
			getRequestFriendResponse.setData(requestFriendResponse);
			getRequestFriendResponse.setCode(Response.CODE_1000);
			getRequestFriendResponse.setMessage(Response.MESSAGE_1000);
			return ResponseEntity.ok(getRequestFriendResponse);
		}else {
			getRequestFriendResponse.setData(null);
			getRequestFriendResponse.setCode(Response.CODE_1004);
			getRequestFriendResponse.setMessage(Response.MESSAGE_1004);
			return ResponseEntity.ok(getRequestFriendResponse);
		}


	}


	// Chấp nhận và hủy yêu cầu kết bạn
	@RequestMapping(value = { "/set_accept_friend" }, method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> set_accept_friend(@RequestParam int user_id,@RequestParam Boolean isAccept,Friend friendData, final ModelMap model,
														   final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		int id = userService.findUserByPhone(phone).getId();
		BaseResponse baseResponse = new BaseResponse();

		if(userService.findFriendRequestById(user_id,id)!=null && ( isAccept==true || isAccept==false) ){
			friendData = userService.findFriendRequestById(user_id, id);
			friendData.setIsAccept(isAccept);

			if(isAccept==false){
				userService.deleteFriendRequest(user_id, id);
			}else{
				Long ts = System.currentTimeMillis()/1000;
				friendData.setCreated(Long.toString(ts));
				userService.saveFriendRequest(friendData);
			}
			baseResponse.setCode(Response.CODE_1000);
			baseResponse.setMessage(Response.MESSAGE_1000);
			return ResponseEntity.ok(baseResponse);
		}else{
			baseResponse.setCode(Response.CODE_1004);
			baseResponse.setMessage(Response.MESSAGE_1004);
			return ResponseEntity.ok(baseResponse);
		}

	}

	// bỏ block



}
