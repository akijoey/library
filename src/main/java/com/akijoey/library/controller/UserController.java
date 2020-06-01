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

    @Autowired
    ResultUtil resultUtil;

    @GetMapping("/info")
    public Map<String, Object> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> info = userService.getInfoByUsername(username);
        return resultUtil.createResult(200, "Get Success", info);
    }

    @GetMapping("/detail")
    public Map<String, Object> getDetail(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> detail = userService.getDetailByUsername(username);
        return resultUtil.createResult(200, "Get Success", detail);
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, String> data) {
//        userService.update();
        return resultUtil.createResult(200, "Update Success");
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestBody Map<String, String> data) {
//        userService.upload();
        return resultUtil.createResult(200, "Upload Success");
    }

    @PostMapping("/passwd")
    public Map<String, Object> passwd(@RequestBody String password) {
//        userService.passwd();
        return resultUtil.createResult(200, "Change Success");
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> data) {
        userService.register(data.get("username"), data.get("password"));
        return resultUtil.createResult(200, "Register Success");
    }
}
