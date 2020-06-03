package com.akijoey.library.service;

import com.akijoey.library.entity.Menu;
import com.akijoey.library.entity.Role;
import com.akijoey.library.entity.User;
import com.akijoey.library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username error");
        }
        return user;
    }

    public Map<String, Object> getInfoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<Role> roles = user.getRoles();
        List<Menu> menus = new ArrayList<>();
        roles.forEach(role -> menus.addAll(role.getMenus()));
        menuService.formatMenus(menus);
        return new HashMap<>(){{
            put("name", username);
            put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            put("routes", menus);
        }};
    }

    public Map<String, Object> getDetailByUsername(String username) {
        return userRepository.findDetailByUsername(username);
    }

    public void insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        String encrypt = passwordEncoder.encode(password);
        user.setPassword(encrypt);
        List<Role> roles = roleService.getUserRoles();
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUser() {

    }

    public void uploadAvatar() {

    }

    public void changePassword(String password, String newPassword) {

    }
}
