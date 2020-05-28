package com.akijoey.library.service;

import com.akijoey.library.entity.Role;
import com.akijoey.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> getUserRoles() {
        Role role = getByName("ROLE_USER");
        return new ArrayList<>(){{
            add(role);
        }};
    }
}
