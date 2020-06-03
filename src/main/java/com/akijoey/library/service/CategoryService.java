package com.akijoey.library.service;

import com.akijoey.library.entity.Category;
import com.akijoey.library.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> list() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Category getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }
}
