package com.akijoey.library.service;

import com.akijoey.library.entity.Menu;
import com.akijoey.library.entity.Role;
import com.akijoey.library.entity.User;
import com.akijoey.library.repository.UserRepository;

import com.akijoey.library.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    FileUtil fileUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username error");
        }
        return user;
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Map<String, Object> getInfoByUsername(String username) {
        User user = getUserByUsername(username);
        String avatar = user.getAvatar();
        List<Role> roles = user.getRoles();
        List<Menu> menus = new ArrayList<>();
        roles.forEach(role -> menus.addAll(role.getMenus()));
        menuService.formatMenus(menus);
        return Map.of("name", username, "avatar", avatar, "routes", menus);
    }

    public long getTotal() {
        return userRepository.count();
    }

    public List<Map<String, Object>> getTable(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Map<String, Object>> table = userRepository.findTable(pageable);
        return new ArrayList<>(){{
            table.forEach(row -> add(new HashMap<>(){{
                putAll(row);
                put("roles", row.get("roles").toString().split(","));
            }}));
        }};
    }

    public Map<String, Object> getDetailByUsername(String username) {
        return userRepository.findDetailByUsername(username);
    }

    public boolean insertUser(String username, String password) {
        if (!existsUserByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            String encrypt = passwordEncoder.encode(password);
            user.setPassword(encrypt);
            List<Role> roles = roleService.getUserRoles();
            user.setRoles(roles);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUser(String username, Map<String, String> data) {
        String newUsername = data.get("username");
        boolean equals = username.equals(newUsername);
        if (equals || !existsUserByUsername(newUsername)) {
            User user = getUserByUsername(username);
            if (!equals) {
                user.setUsername(newUsername);
            }
            user.setPhone(data.get("phone"));
            user.setAddress(data.get("address"));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String uploadAvatar(String username, MultipartFile image) {
        String avatar = fileUtil.uploadImage(image);
        if (avatar != null) {
            User user = getUserByUsername(username);
            fileUtil.deleteImage(user.getAvatar());
            user.setAvatar(avatar);
            userRepository.save(user);
        }
        return avatar;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = getUserByUsername(username);
        String password = user.getPassword();
        if (passwordEncoder.matches(oldPassword, password)) {
            String encrypt = passwordEncoder.encode(newPassword);
            user.setPassword(encrypt);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void initializePassword(String username, String password) {
        User user = getUserByUsername(username);
        String encrypt = passwordEncoder.encode(password);
        user.setPassword(encrypt);
        userRepository.save(user);
    }

    public void enableUserById(int id) {
        User user = getUserById(id);
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

}
