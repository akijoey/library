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

    public void saveUser(User user) {
        userRepository.save(user);
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

    public Map<String, Object> getMajorByUsername(String username) {
        return userRepository.findMajorByUsername(username);
    }

    public boolean registerUser(String username, String password) {
        if (!existsUserByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            String encrypt = passwordEncoder.encode(password);
            user.setPassword(encrypt);
            List<Role> roles = roleService.getUserRoles();
            user.setRoles(roles);
            saveUser(user);
            return true;
        }
        return false;
    }

    public boolean reportUser(String username, Map<String, Object> data) {
        String newUsername = (String)data.get("username");
        boolean equals = username.equals(newUsername);
        if (equals || !existsUserByUsername(newUsername)) {
            User user = getUserByUsername(username);
            if (!equals) {
                user.setUsername(newUsername);
            }
            user.setPhone((String)data.get("phone"));
            user.setAddress((String)data.get("address"));
            saveUser(user);
            return true;
        }
        return false;
    }

    public String replaceAvatar(String username, MultipartFile image) {
        String avatar = fileUtil.uploadImage(image);
        if (avatar != null) {
            User user = getUserByUsername(username);
            fileUtil.deleteImage(user.getAvatar());
            user.setAvatar(avatar);
            saveUser(user);
        }
        return avatar;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = getUserByUsername(username);
        String password = user.getPassword();
        if (passwordEncoder.matches(oldPassword, password)) {
            String encrypt = passwordEncoder.encode(newPassword);
            user.setPassword(encrypt);
            saveUser(user);
            return true;
        }
        return false;
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

    public Map<String, Object> getDetailById(int id) {
        return userRepository.findDetailById(id);
    }

    public boolean insertUser(Map<String, Object> data) {
        String username = (String)data.get("username");
        String password = (String)data.get("password");
        if (registerUser(username, password)) {
            reportUser(username, data);
            User user = getUserByUsername(username);
            user.setAvatar((String)data.get("avatar"));
            saveUser(user);
            return true;
        }
        return false;
    }

    public void enableUserById(int id) {
        User user = getUserById(id);
        user.setEnabled(!user.getEnabled());
        saveUser(user);
    }

    public void initializePassword(int id, String password) {
        User user = getUserById(id);
        String encrypt = passwordEncoder.encode(password);
        user.setPassword(encrypt);
        saveUser(user);
    }

    public String uploadAvatar(MultipartFile image) {
        return fileUtil.uploadImage(image);
    }

    public boolean updateUser(Map<String, Object> data) {
        User user = getUserById((Integer)data.get("id"));
        String username = user.getUsername();
        if (reportUser(username, data)) {
            user.setAvatar((String)data.get("avatar"));
            saveUser(user);
            return true;
        }
        return false;
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
