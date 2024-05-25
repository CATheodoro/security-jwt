package com.theodoro.security.domain.services;

import com.theodoro.security.domain.entities.Role;
import com.theodoro.security.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    public Role save(Role role) {
        return repository.save(role);
    }

    public Optional<Role> findById(Integer id) {
        return repository.findById(id);
    }
}
