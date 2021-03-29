package com.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.web.services.ResponseService;

public abstract class BaseController {
	@Autowired
	public ResponseService responseService;

}
