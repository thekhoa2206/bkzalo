package com.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.Response.AjaxResponse;
import com.web.Response.LoginResponse;
import com.web.entities.Response;
import com.web.entities.User;
import com.web.services.UserService;

@RestController
public class LoginController {
	@Autowired
	UserService userService;
//login
	@PostMapping(value = { "/login" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> login(@RequestParam String phone, @RequestParam String password,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		if (phone != null && password != null) {
			if (phone != null) {
				if (password != null) {
					if (phone.length() == 10 && Character.toString(phone.charAt(0)).equals("0")) {
						if (userService.getSpecialCharacterCount(password) == true && password.length() <= 10
								&& password.length() >= 6 && password.compareTo(phone) != 0) {
							if (userService.findUserByPhone(phone) != null) {
								if (password.compareTo(userService.findUserByPhone(phone).getPassword()) == 0) {
									User data = userService.findUserByPhone(phone);
									data.setToken(userService.createJWT(data.getPhone()));
									LoginResponse dataLogin = new LoginResponse(Integer.toString(data.getId()),
											data.getPhone(), data.getToken(), data.getAvatar());
									return ResponseEntity
											.ok(new AjaxResponse(Response.CODE_1000, Response.MESSAGE_1000, dataLogin));
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
	@PostMapping(value = { "/logout" }, produces = "application/json")
	public ResponseEntity<AjaxResponse> logout(@RequestBody User data, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		data = null;
		return ResponseEntity.ok(new AjaxResponse(200, "Logout success!!", data));
	}
}
