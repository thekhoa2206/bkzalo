package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.entities.Response;
import com.web.entities.User;
import com.web.repositories.RoleRepo;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

@RestController
public class SignUpController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@PostMapping(value = { "/signup" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> signup(@RequestParam String password, @RequestParam String phone,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		List<User> users = userRepo.findAll();
		String message = null;
<<<<<<< HEAD
		if (phone != null && password != null) {
			if (phone != password) {
				for (User user : users) {
					if (phone == user.getPhone()) {
=======
		if (data.getPhone() != null && data.getPassword() != null) {
			if (data.getPhone() != data.getPassword()) {			//Nếu mật khẩu không trùng phone

				for (User user : users) {
					if (data.getPhone() == user.getPhone()) {    //Nếu số điện thoại đã tồn tại
>>>>>>> f3f9e3f0fb5238433cbb4d267392bebb8a99fff3
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_9996, Response.MESSAGE_9996));
					}
				}

<<<<<<< HEAD
				if (userService.getSpecialCharacterCount(password) == true) {
					if (phone.length() == 10 && Character.toString(phone.charAt(0)).equals("0")) {
						if (password.length() >= 6 && password.length() <= 10) {
							User data = new User();
							data.setPhone(phone);
							data.setPassword(password);
=======
				if (userService.getSpecialCharacterCount(data.getPassword()) == true) {   //Nếu không chứa kí tự đặc biệt
					if (data.getPhone().length() == 10 && Character.toString(data.getPhone().charAt(0)).equals("0")) {	//Nếu độ dài phone = 10 và bắt đầu bằng 0
						if (data.getPassword().length() >= 6 && data.getPassword().length() <= 10) {	//Nếu 6 <= pass <= 10
>>>>>>> f3f9e3f0fb5238433cbb4d267392bebb8a99fff3
							data.setRoles(userService.findRoleById(1));
							userService.saveGuestUser(data);
							return ResponseEntity.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, data));
						} else {
							return ResponseEntity.ok(new AjaxResponse(Response.CODE_1015, Response.MESSAGE_1015));
						}
					} else {

						System.out.println(message);
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1014, Response.MESSAGE_1014));
					}
				} else {
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1013, Response.MESSAGE_1013));
				}

			} else {
				return ResponseEntity.ok(new AjaxResponse(Response.CODE_1012, Response.MESSAGE_1012));
			}
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1011, Response.MESSAGE_1011));
		}
	}
}
