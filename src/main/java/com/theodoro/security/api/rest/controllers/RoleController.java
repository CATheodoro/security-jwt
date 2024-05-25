package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.RoleAssembler;
import com.theodoro.security.api.rest.models.requests.RoleRequest;
import com.theodoro.security.api.rest.models.responses.RoleResponse;
import com.theodoro.security.domain.entities.Role;
import com.theodoro.security.domain.exceptions.ConflictException;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.services.RoleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ROLE_ALREADY_EXISTS;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ROLE_ID_NOT_FOUND;

@Controller
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    public static final String ROLE_RESOURCE_PATH = "/api/v1/role";
    public static final String ROLE_SELF_PATH = ROLE_RESOURCE_PATH + "/{id}";

    private final RoleService roleService;
    private final RoleAssembler roleAssembler;

    public RoleController(RoleService roleService, RoleAssembler roleAssembler) {
        this.roleService = roleService;
        this.roleAssembler = roleAssembler;
    }

    @GetMapping(ROLE_RESOURCE_PATH)
    private ResponseEntity<List<RoleResponse>> findAll() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roleAssembler.toListModel(roles));
    }

    @PostMapping(ROLE_RESOURCE_PATH)
    private ResponseEntity<RoleResponse> save(@RequestBody @Valid RoleRequest request) {
        roleService.findByName(request.getName()).ifPresent(searchedRule -> {
            logger.info("Role {} already exist.", request.getName());
            throw new ConflictException(ROLE_ALREADY_EXISTS,
                    roleAssembler.buildSelfLink(searchedRule.getId()).toUri());
        });

        Role role = roleAssembler.toEntity(request);
        role = roleService.save(role);
        return ResponseEntity.created(roleAssembler.buildSelfLink(role.getId()).toUri()).build();
    }

    @GetMapping(ROLE_SELF_PATH)
    public ResponseEntity<RoleResponse> findById(@PathVariable("id") final String id) {
        Role role = roleService.findById(id).orElseThrow(() ->{
            logger.info("Role with ID {} not found.", id);
            return new NotFoundException(ROLE_ID_NOT_FOUND);
        });
        return ResponseEntity.ok().body(roleAssembler.toModel(role));
    }
}