package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.web.entities.AjaxResponse;
import com.web.entities.User;
import com.web.services.UserService;

@RestController
public class SignUpController {
	
	@Autowired
	UserService userService;

	@PostMapping(value = { "/signup" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> saveGuestUser(@RequestBody User data, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		userService.saveGuestUser(data);
		return ResponseEntity.ok(new AjaxResponse(200,"Sign Up success!!" , data));
	}
}
