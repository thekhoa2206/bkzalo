package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.web.entities.AjaxResponse;
import com.web.services.UserService;

@RestController
public class SignUpController {
	
	@Autowired
	UserService userService;

	@GetMapping(value = { "/signup" }, produces = "application/json")
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return ResponseEntity.ok(new AjaxResponse(200,"OK" , data));
	}
	@PostMapping(value = { "/save-guestUser" }, produces = "application/json")
	public String saveGuestUser(@ModelAttribute("user") User user, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		userService.saveGuestUser(user);
		return "redirect:/home";
	}
}
