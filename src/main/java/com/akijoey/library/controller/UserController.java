package com.akijoey.library.controller;

import com.akijoey.library.service.UserService;
import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
        if (!userService.registerUser(data.get("username"), data.get("password"))) {
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

    @GetMapping("/major")
    public Map<String, Object> getMajor(@RequestHeader("Authorization") String authorization) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        Map<String, Object> major = userService.getMajorByUsername(username);
        return resultUtil.successResult("Get Success", Map.of("major", major));
    }

    @PostMapping("/report")
    public Map<String, Object> report(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Object> data) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        if (!userService.reportUser(username, data)) {
            return resultUtil.customResult(401, "Username Existed");
        }
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/replace")
    public Map<String, Object> replace(@RequestHeader("Authorization") String authorization, @RequestParam("avatar") MultipartFile image) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        String avatar = userService.replaceAvatar(username, image);
        if (avatar == null) {
            return resultUtil.customResult(500, "Upload Failure");
        }
        return resultUtil.successResult("Upload Success", Map.of("avatar", avatar));
    }

    @PostMapping("/passwd")
    public Map<String, Object> passwd(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, String> data) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        if (!userService.changePassword(username, data.get("oldPassword"), data.get("newPassword"))) {
            return resultUtil.customResult(401, "Password Error");
        }
        return resultUtil.successResult("Change Success");
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getTotal() {
        long total = userService.getTotal();
        return resultUtil.successResult("Get Success", Map.of("total", total));
    }

    @GetMapping("/table/{page}/{size}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getTable(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Map<String, Object>> table = userService.getTable(page, size);
        return resultUtil.successResult("Get Success", Map.of("table", table));
    }

    @PostMapping("/detail")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getDetail(@RequestBody Map<String, Integer> data) {
        Map<String, Object> detail = userService.getDetailById(data.get("id"));
        return resultUtil.successResult("Get Success", Map.of("detail", detail));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> insert(@RequestBody Map<String, Object> data) {
        if (!userService.insertUser(data)) {
            return resultUtil.customResult(401, "Username Existed");
        }
        return resultUtil.successResult("Insert Success");
    }

    @PostMapping("/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> enable(@RequestBody Map<String, Integer> data) {
        userService.enableUserById(data.get("id"));
        return resultUtil.successResult("Enable Success");
    }

    @PostMapping("/initialize")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> initialize(@RequestBody Map<String, Object> data) {
        userService.initializePassword((Integer)data.get("id"), (String)data.get("password"));
        return resultUtil.successResult("Initialize Success");
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> upload(@RequestParam("avatar") MultipartFile image) {
        String avatar = userService.uploadAvatar(image);
        if (avatar == null) {
            return resultUtil.customResult(500, "Upload Failure");
        }
        return resultUtil.successResult("Upload Success", Map.of("avatar", avatar));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> update(@RequestBody Map<String, Object> data) {
        if (!userService.updateUser(data)) {
            return resultUtil.customResult(401, "Username Existed");
        }
        return resultUtil.successResult("Update Success");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> delete(@RequestBody Map<String, Integer> data) {
        userService.deleteUserById(data.get("id"));
        return resultUtil.successResult("Delete Success");
    }

}
