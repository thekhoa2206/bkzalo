package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.entities.User;
import com.web.services.BlockService;

@RestController
public class BlockController {
	@Autowired
	BlockService blockService;
	
	@PostMapping(value = { "/set_block_user" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_block_user(@RequestBody User data, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		return ResponseEntity.ok(new AjaxResponse(1000,"OK" , data));
	}
	
	@PostMapping(value = { "/set_block_diary" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> set_block_diary(@RequestBody User data, final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		
		return ResponseEntity.ok(new AjaxResponse(1000,"OK" , data));
	}
	
}
