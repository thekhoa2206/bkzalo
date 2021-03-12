package com.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	
		@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
		public ResponseEntity<AjaxResponse> get_user_info(@RequestBody User data
		,final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
			model.addAttribute("users", data);
			return ResponseEntity.ok(new AjaxResponse(200, data));
		}
		
		@RequestMapping(value = { "/edit_users/{id}" }, method = RequestMethod.GET)
		public ResponseEntity<AjaxResponse> set_user_info(@PathVariable("id") int id, @RequestBody User data
		,final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
			model.addAttribute("users", data);
			model.addAttribute("user", userService.findUserById(id));
			return ResponseEntity.ok(new AjaxResponse(200, data));
		}
		
		
}
