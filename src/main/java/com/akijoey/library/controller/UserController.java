package com.akijoey.library.controller;

import com.akijoey.library.service.UserService;
import com.akijoey.library.util.ResultUtil;
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
    public Map<String, Object> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> info = userService.getInfoByUsername(username);
        return ResultUtil.createResult(200, "Get Success", info);
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody String username, @RequestBody String password) {
        userService.register(username, password);
        return ResultUtil.createResult(200, "Register Success");
    }
}
