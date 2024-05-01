package com.theodoro.security.service;

import com.theodoro.security.repository.UserRepository;
import com.theodoro.security.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserResponse> getAll() {
        var user = repository.findAll();
        return UserResponse.convertResponse(user);
    }
}
