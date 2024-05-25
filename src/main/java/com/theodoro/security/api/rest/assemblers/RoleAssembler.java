package com.theodoro.security.api.rest.assemblers;

import com.theodoro.security.api.rest.controllers.RoleController;
import com.theodoro.security.api.rest.models.requests.RoleRequest;
import com.theodoro.security.api.rest.models.responses.RoleResponse;
import com.theodoro.security.domain.entities.Role;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleAssembler extends RepresentationModelAssemblerSupport<Role, RoleResponse> {

    public RoleAssembler() {
        super(RoleController.class, RoleResponse.class);
    }

    @Override
    public RoleResponse toModel(@NonNull Role role) {
        RoleResponse response = new RoleResponse(role);
        response.add(this.buildSelfLink(role.getId()));
        return response;
    }

    public Link buildSelfLink(String id) {
        return linkTo(methodOn(RoleController.class).findById(id)).withSelfRel();
    }

    public Role toEntity(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName().toUpperCase());
        role.setDescription(request.getDescription());
        return role;
    }

    public List<RoleResponse> toListModel(List<Role> roles) {
        return roles.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}