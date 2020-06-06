package com.akijoey.library.controller;

import com.akijoey.library.service.CategoryService;
import com.akijoey.library.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ResultUtil resultUtil;

    @GetMapping("/side")
    public Map<String, Object> getSide() {
        List<Map<String, Object>> side = categoryService.getSide();
        return resultUtil.successResult("Get Success", Map.of("side", side));
    }

}
