package com.akijoey.library.controller;

import com.akijoey.library.service.UserService;
import com.akijoey.library.util.FileUtil;
import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    @Autowired
    FileUtil fileUtil;

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
    public Map<String, Object> upload(HttpServletRequest request, @RequestParam("avatar") MultipartFile file) {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\";
        String name = fileUtil.uploadFile(path, file);
        if (name == null) {
            return resultUtil.customResult(500, "Upload Failure");
        }
        String username = tokenUtil.getSubject(request.getHeader("Authorization").replace("Bearer ", ""));
        String avatar = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img/" + name;
        userService.uploadAvatar(username, avatar);
        return resultUtil.successResult("Upload Success", Map.of("avatar", avatar));
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, String> data) {
        userService.updateUser();
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/passwd")
    public Map<String, Object> passwd(HttpServletRequest request, @RequestBody Map<String, String> data) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = tokenUtil.getSubject(token);
        if (!userService.changePassword(username, data.get("oldPassword"), data.get("newPassword"))) {
            return resultUtil.customResult(401, "Password Error");
        }
        return resultUtil.successResult("Change Success");
    }

}
