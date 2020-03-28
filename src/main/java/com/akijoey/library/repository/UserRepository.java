package com.akijoey.library.repository;

import com.akijoey.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
}
