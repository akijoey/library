package com.akijoey.library.controller;

import com.akijoey.library.entity.User;
import com.akijoey.library.result.Result;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@RestController
public class LoginController {

    @CrossOrigin
    @RequestMapping("/api/login")
    public Result login(@RequestBody User request) {
        // 转义 username, 防止 XSS
        String username = request.getUsername();
        username = HtmlUtils.htmlEscape(username);

        if (!Objects.equals("admin", username) || !Objects.equals("123456", request.getPassword())) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
