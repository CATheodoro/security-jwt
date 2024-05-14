package com.theodoro.security.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.models.Role;
import com.theodoro.security.models.User;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "roles"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "user", collectionRelation = "users")
public class UserResponse extends RepresentationModel<UserResponse> {
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
