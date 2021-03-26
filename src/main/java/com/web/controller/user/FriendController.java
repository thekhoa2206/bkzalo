package com.web.controller.user;

import com.web.entities.AjaxResponse;
import com.web.entities.User;
import com.web.repositories.UserRepo;
import com.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FriendController {
    @Autowired
    public UserRepo userRepo;
    @Autowired
    UserService userService;

    //	Lấy thông tin FriendRequest
    @PostMapping(value = { "/get_friend_request_info/{id}" }, produces = "application/json")
    public ResponseEntity<AjaxResponse> get_friend_request_info(@PathVariable("id") int id, @RequestBody User data,
                                                      final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

        return ResponseEntity.ok(new AjaxResponse(200, "Success!", userService.findFriendById(id)));
    }

}
