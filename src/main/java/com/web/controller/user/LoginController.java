package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.web.Response.AjaxResponse;
=======

import com.web.Response.AjaxResponse;

>>>>>>> 18e6d9d0274d9aaf2ddf6e5b8d2ada0a213258c9
import com.web.entities.User;
import com.web.services.UserService;

@RestController
public class LoginController {
	@Autowired
	UserService userService;

//login
	@PostMapping(value = { "/login" }, produces = "application/json")

	public ResponseEntity<AjaxResponse> login(@RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String phone = data.getPhone();
		if (userService.findUserByPhone(phone).getPassword().compareTo(data.getPassword()) == 0) {
			data = userService.findUserByPhone(phone);
			data.setToken(userService.createJWT(data.getPhone()));
			System.out.println(userService.createJWT(data.getPhone()));
			return ResponseEntity.ok(new AjaxResponse(200, "Login success!!", data));
		} else {
			data.setPassword(null);
			data.setPhone(null);
			return ResponseEntity.ok(new AjaxResponse(200, "Login fail!!", data));
		}
	}

//logout
	@GetMapping(value = { "/logout" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> logout(@RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		data = null;
		return ResponseEntity.ok(new AjaxResponse(200, "Logout success!!", data));
	}
}
