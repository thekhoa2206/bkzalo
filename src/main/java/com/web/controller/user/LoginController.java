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

import com.web.entities.AjaxResponse;
import com.web.entities.User;
import com.web.services.UserService;

@RestController
public class LoginController {
	@Autowired
	UserService userService;

	@PostMapping(value = { "/login" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> login(@RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String phone = data.getPhone();
		if (userService.finUserByPhone(phone).getPassword().compareTo(data.getPassword()) == 0) {
			data = userService.finUserByPhone(phone);
			return ResponseEntity.ok(new AjaxResponse(200, "Login success!!", data));
		} else {
			data.setPassword(null);
			data.setPhone(null);
			return ResponseEntity.ok(new AjaxResponse(200, "Login fail!!", data));
		}
	}

	@GetMapping(value = { "/logout" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> logout(@RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {		
		return ResponseEntity.ok(new AjaxResponse(200, "Logout success!!", data));
	}

}
