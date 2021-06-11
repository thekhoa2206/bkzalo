package com.web.controller.user;

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
import com.web.Response.UserResponse;
import com.web.entities.Response;
import com.web.entities.User;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

@RestController
public class UserController extends BaseController {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	public UserService userService;

//Lấy thông tin User
	@GetMapping(value = { "/get_user_info/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_user_info(@PathVariable("id") int id, @RequestBody User data,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		data = userService.findUserById(id);
		String token = request.getHeader("Authorization");
		String phone = userService.getPhoneNumberFromToken(token);
		return ResponseEntity.ok(new AjaxResponse(1000, "OK", data));
	}
	//Lấy thông tin User
		@PostMapping(value = { "/change_password" }, produces = "application/json")
		public ResponseEntity<AjaxResponse> change_password(@RequestParam String token, @RequestParam String password,@RequestParam String new_password,
				final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
			User data = userService.findUserByPhone(userService.getPhoneNumberFromToken(token));
			if(password.compareTo(data.getPassword()) == 0) {
				data.setPassword(new_password);
				try {
					userService.saveUser(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ResponseEntity.ok(new AjaxResponse(1000, "OK", data));
			}else {
				return ResponseEntity.ok(new AjaxResponse(1026, "Sai Mật Khẩu"));
			}
			
		}
		
//Sửa thông tin của user
	@PostMapping(value = { "/set_user_info" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_user_info(@RequestParam String token, @RequestParam String name, @RequestParam String avatar, 
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		User user = userService.findUserByPhone(userService.getPhoneNumberFromToken(token));
		User data = new User();
		data.setName(name);
		data.setAvatar(avatar);
//Không xóa thông tin xong DB
		if (data.getName() != null) {
			user.setName(data.getName());
		}
		if (data.getPhone() != null) {
			user.setPhone(data.getPhone());
		}
//Không xóa thông tin xong DB
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserResponse userResponse = new UserResponse(data.getName(), data.getAvatar());
		return ResponseEntity.ok(new AjaxResponse(1000, "OK", userResponse));
	}
}
