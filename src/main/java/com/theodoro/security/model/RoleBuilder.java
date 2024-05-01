package com.theodoro.security.model;

import java.time.LocalDateTime;
import java.util.List;

public final class RoleBuilder {
    private Integer id;
    private String name;
    private List<User> user;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private RoleBuilder() {
    }

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public RoleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder user(List<User> user) {
        this.user = user;
        return this;
    }

    public RoleBuilder createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public RoleBuilder lastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public Role build() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setUser(user);
        role.setCreatedDate(createdDate);
        role.setLastModifiedDate(lastModifiedDate);
        return role;
    }
}
