package com.akijoey.library.controller;

import com.akijoey.library.response.ResponseBody;
import com.akijoey.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/info")
    public ResponseBody getInfo() {
        // get user info and routes
        return new ResponseBody(200, "Get Success", null);
    }

    @PostMapping("/register")
    public ResponseBody register(@RequestBody String username, @RequestBody String password) {
        // register
        return new ResponseBody(200, "Register Success", null);
    }
}
