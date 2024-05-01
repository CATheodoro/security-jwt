package com.theodoro.security.model;

import java.time.LocalDateTime;
import java.util.List;

public final class UserBuilder {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean enabled;
    private List<Role> roles;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder accountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
        return this;
    }

    public UserBuilder enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserBuilder roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public UserBuilder lastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAccountLocked(accountLocked);
        user.setEnabled(enabled);
        user.setRoles(roles);
        user.setCreatedDate(createdDate);
        user.setLastModifiedDate(lastModifiedDate);
        return user;
    }
}
