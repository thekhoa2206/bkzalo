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
import com.web.entities.Response;
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
		if (data.getPhone() != null && data.getPassword() != null) {
			if (data.getPhone() != null) {
				if (data.getPassword() != null) {
					if (data.getPhone().length() == 10 && Character.toString(data.getPhone().charAt(0)).equals("0")) {
						if (userService.getSpecialCharacterCount(data.getPassword()) == true
								&& data.getPassword().length() <= 10 && data.getPassword().length() >= 6
								&& data.getPassword().compareTo(data.getPhone()) != 0) {
							if (userService.finUserByPhone(data.getPhone()) != null) {
								if (data.getPassword()
										.compareTo(userService.finUserByPhone(data.getPhone()).getPassword()) == 0) {
									data = userService.finUserByPhone(data.getPhone());
									data.setToken(userService.createJWT(data.getPhone()));
									return ResponseEntity
											.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, data));
								} else {
									return ResponseEntity
											.ok(new AjaxResponse(Response.CODE_1021, Response.MESSAGE_1021));
								}
							} else {
								return ResponseEntity.ok(new AjaxResponse(Response.CODE_9995, Response.MESSAGE_9995));
							}

						} else {
							return ResponseEntity.ok(new AjaxResponse(Response.CODE_1019, Response.MESSAGE_1019));
						}

					} else {
						return ResponseEntity.ok(new AjaxResponse(Response.CODE_1018, Response.MESSAGE_1018));
					}
				} else {
					return ResponseEntity.ok(new AjaxResponse(Response.CODE_1019, Response.MESSAGE_1019));
				}
			} else {
				return ResponseEntity.ok(new AjaxResponse(Response.CODE_1018, Response.MESSAGE_1018));
			}
		} else {
			return ResponseEntity.ok(new AjaxResponse(Response.CODE_1018, Response.MESSAGE_1018));
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
