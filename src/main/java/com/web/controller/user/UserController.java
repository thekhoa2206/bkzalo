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
import org.springframework.web.bind.annotation.RestController;

import com.web.entities.AjaxResponse;
import com.web.entities.User;
import com.web.repositories.UserRepo;
import com.web.services.UserService;

@RestController
public class UserController extends BaseController {
	@Autowired
	public UserRepo userRepo;
	@Autowired
	public UserService userService;

//	Lấy thông tin User
	@GetMapping(value = { "/get_user_info/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> get_user_info(@PathVariable("id") int id, @RequestBody User data,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		data = userService.findUserById(id);
		return ResponseEntity.ok(new AjaxResponse(200,"Success!", data));
	}

//	Sửa thông tin của user
	@PostMapping(value = { "/set_user_info/{id}" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_user_info(@PathVariable("id") int id, @RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {
		data.setId(id);
		try {
			userService.saveUser(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new AjaxResponse(200,"Success!", data));
	}
	

}
