package com.akijoey.library.controller;

import com.akijoey.library.service.UserService;
import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
        if (!userService.insertUser(data.get("username"), data.get("password"))) {
            return resultUtil.customResult(401, "Username Existed");
        }
        return resultUtil.successResult("Register Success");
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo(@RequestHeader("Authorization") String authorization) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        Map<String, Object> info = userService.getInfoByUsername(username);
        return resultUtil.successResult("Get Success", info);
    }

    @GetMapping("/total")
    public Map<String, Object> getTotal() {
        long total = userService.getTotal();
        return resultUtil.successResult("Get Success", Map.of("total", total));
    }

    @GetMapping("/table/{page}/{size}")
    public Map<String, Object> getTable(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Map<String, Object>> table = userService.getTable(page, size);
        return resultUtil.successResult("Get Success", Map.of("table", table));
    }

    @GetMapping("/detail")
    public Map<String, Object> getDetail(@RequestHeader("Authorization") String authorization) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        Map<String, Object> detail = userService.getDetailByUsername(username);
        return resultUtil.successResult("Get Success", Map.of("detail", detail));
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestHeader("Authorization") String authorization, @RequestParam("avatar") MultipartFile image) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        String avatar = userService.uploadAvatar(username, image);
        if (avatar == null) {
            return resultUtil.customResult(500, "Upload Failure");
        }
        return resultUtil.successResult("Upload Success", Map.of("avatar", avatar));
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, String> data) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        if (!userService.updateUser(username, data)) {
            return resultUtil.customResult(401, "Username Existed");
        }
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/passwd")
    public Map<String, Object> passwd(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, String> data) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        if (!userService.changePassword(username, data.get("oldPassword"), data.get("newPassword"))) {
            return resultUtil.customResult(401, "Password Error");
        }
        return resultUtil.successResult("Change Success");
    }

    @PostMapping("/initialize")
    public Map<String, Object> initialize(@RequestBody Map<String, String> data) {
        userService.initializePassword(data.get("username"), data.get("password"));
        return resultUtil.successResult("Initialize Success");
    }

    @PostMapping("/enable")
    public Map<String, Object> enable(@RequestBody Map<String, Integer> data) {
        userService.enableUserById(data.get("id"));
        return resultUtil.successResult("Enable Success");
    }

}
