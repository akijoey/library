package com.akijoey.library.repository;

import com.akijoey.library.entity.Menu;
import com.akijoey.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findAll();
}
