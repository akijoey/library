package com.akijoey.library.service;

import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Map<String, Object>> getSide() {
        return categoryRepository.findSide();
    }

    public List<Map<String, Object>> getList() {
        return categoryRepository.findList();
    }

}
