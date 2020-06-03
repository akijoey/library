package com.akijoey.library.service;

import com.akijoey.library.entity.Menu;
import com.akijoey.library.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public List<Menu> getMenusByPid(int pid) {
        return menuRepository.findAllByPid(pid);
    }

    public void formatMenus(List<Menu> menus) {
        menus.forEach(menu -> {
            List<Menu> children = getMenusByPid(menu.getId());
            menu.setChildren(children);
        });
        menus.removeIf(menu -> menu.getPid() != 0);
    }
}
