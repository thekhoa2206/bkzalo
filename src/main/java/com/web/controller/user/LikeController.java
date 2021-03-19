package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.entities.AjaxResponse;
import com.web.entities.User;

@RestController
public class LikeController {
	
	@PostMapping(value = { "/like" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> like(@PathVariable("id") int id, @RequestBody User data,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

		return ResponseEntity.ok(new AjaxResponse(200,"Success!", data));
	}

}
