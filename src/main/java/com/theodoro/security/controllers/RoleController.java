package com.theodoro.security.controllers;

import com.theodoro.security.assemblers.RoleAssembler;
import com.theodoro.security.models.Role;
import com.theodoro.security.requests.RoleRequest;
import com.theodoro.security.responses.RoleResponse;
import com.theodoro.security.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/role")
public class RoleController {


    private final RoleService service;
    private final RoleAssembler roleAssembler;

    public RoleController(RoleService service, RoleAssembler roleAssembler) {
        this.service = service;
        this.roleAssembler = roleAssembler;
    }

    @GetMapping
    private ResponseEntity<List<RoleResponse>> findAll() {
        List<Role> roles = service.findAll();
        return ResponseEntity.ok(roleAssembler.toListModel(roles));
    }

    @PostMapping
    private ResponseEntity<RoleResponse> save(@RequestBody @Valid RoleRequest request) {
        service.findByName(request.getName()).ifPresent(searchedRule -> {
            throw new IllegalArgumentException("Role " + request.getName() + " already exist, " +
                roleAssembler.buildSelfLink(searchedRule.getId()).toUri());
            });

        Role role = roleAssembler.toEntity(request);
        role = service.save(role);
        return ResponseEntity.created(roleAssembler.buildSelfLink(role.getId()).toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable("id") final Integer id) {
        Role role = service.findById(id);
        return ResponseEntity.ok().body(roleAssembler.toModel(role));
    }
}
