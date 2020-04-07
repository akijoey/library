package com.akijoey.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "uid", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "rid", referencedColumnName = "id") }
    )
    private List<User> users;

    @ManyToMany
    @JoinTable(
        name = "menu_role",
        joinColumns = { @JoinColumn(name = "mid", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "rid", referencedColumnName = "id") }
    )
    private List<Menu> menus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
