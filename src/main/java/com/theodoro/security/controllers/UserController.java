package com.theodoro.security.controllers;

import com.theodoro.security.assemblers.UserAssembler;
import com.theodoro.security.models.User;
import com.theodoro.security.responses.UserResponse;
import com.theodoro.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;
    private final UserAssembler userAssembler;

    public UserController(UserService service, UserAssembler userAssembler) {
        this.service = service;
        this.userAssembler = userAssembler;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        var users = service.findAll();
        return ResponseEntity.ok(userAssembler.toListModel(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") final Integer id) {
        User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("User ID was not Found"));
        return ResponseEntity.ok(userAssembler.toModel(user));
    }
}
