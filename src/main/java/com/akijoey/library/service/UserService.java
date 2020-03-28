package com.akijoey.library.service;

import com.akijoey.library.entity.User;
import com.akijoey.library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean isExist(String username) {
        User user = getByName(username);
        return user != null;
    }

    public User getByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userRepository.save(user);
    }
}
