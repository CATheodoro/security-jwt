package com.theodoro.security.assemblers;

import com.theodoro.security.controllers.UserController;
import com.theodoro.security.models.Role;
import com.theodoro.security.models.User;
import com.theodoro.security.requests.RegisterRequest;
import com.theodoro.security.responses.UserResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserResponse> {


    private final PasswordEncoder passwordEncoder;

    public UserAssembler(PasswordEncoder passwordEncoder) {
        super(UserController.class, UserResponse.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse toModel(@NonNull User entity) {
        UserResponse response = new UserResponse(entity);
        response.add(this.buildSelfLink(response.getId()));
        return response;
    }

    public Link buildSelfLink(Integer id) {
        return linkTo(methodOn(UserController.class).findById(id)).withSelfRel();
    }

    public User toEntity(RegisterRequest request, Role role) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(role));
        user.setAccountLocked(false);
        user.setEnabled(false);
        return user;
    }

    public List<UserResponse> toListModel(List<User> users) {
        return users.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
