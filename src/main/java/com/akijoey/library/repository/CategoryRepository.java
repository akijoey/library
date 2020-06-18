package com.akijoey.library.repository;

import com.akijoey.library.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select name as title, icon as icon from Category")
    List<Map<String, Object>> findSide();

    @Query(value = "select id as id, name as name from Category")
    List<Map<String, Object>> findList();

}
