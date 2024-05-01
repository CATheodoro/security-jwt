package com.theodoro.security.response;

import com.theodoro.security.model.Role;
import com.theodoro.security.model.User;

import java.util.List;

public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private List<Role> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static List<UserResponse> convertResponse(List<User> users) {
        return users.stream().map(UserResponse::new).toList();
    }
}
