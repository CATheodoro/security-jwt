package com.theodoro.security.services;

import com.theodoro.security.models.User;
import com.theodoro.security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }
}
