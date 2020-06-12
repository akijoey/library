package com.akijoey.library.controller;

import com.akijoey.library.service.UserService;
import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> data) {
        userService.insertUser(data.get("username"), data.get("password"));
        return resultUtil.successResult("Register Success");
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> info = userService.getInfoByUsername(username);
        return resultUtil.successResult("Get Success", info);
    }

    @GetMapping("/detail")
    public Map<String, Object> getDetail(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        Map<String, Object> detail = userService.getDetailByUsername(username);
        return resultUtil.successResult("Get Success", detail);
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile avatar) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        userService.uploadAvatar(username, avatar);
        return resultUtil.successResult("Upload Success");
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, String> data) {
        userService.updateUser();
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/passwd")
    public Map<String, Object> passwd(@RequestBody Map<String, String> data) {
        userService.changePassword(data.get("password"), data.get("newPassword"));
        return resultUtil.successResult("Change Success");
    }

}
