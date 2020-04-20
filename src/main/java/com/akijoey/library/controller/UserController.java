package com.akijoey.library.controller;

import com.akijoey.library.entity.User;
import com.akijoey.library.util.ResponseBody;
import com.akijoey.library.service.UserService;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping("/info")
    public ResponseBody getInfo(HttpServletRequest request) {
        // get user info and routes
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> info = userService.getInfoByUsername(username);
        return new ResponseBody(200, "Get Success", "lalala");
    }

    @PostMapping("/register")
    public ResponseBody register(@RequestBody String username, @RequestBody String password) {
        // register
        userService.register(username, password);
        return new ResponseBody(200, "Register Success", null);
    }
}
