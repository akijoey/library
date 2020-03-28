package com.akijoey.library.controller;

import com.akijoey.library.entity.User;
import com.akijoey.library.result.Result;
import com.akijoey.library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping("/api/login")
    public Result login(@RequestBody User request) {
        // 转义 username, 防止 XSS
        String username = request.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.getUser(username, request.getPassword());

        if (user == null) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
