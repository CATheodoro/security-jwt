package com.theodoro.security.assemblers;

import com.theodoro.security.controllers.RoleController;
import com.theodoro.security.models.Role;
import com.theodoro.security.requests.RoleRequest;
import com.theodoro.security.responses.RoleResponse;
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
    public RoleResponse toModel(@NonNull Role entity) {
        RoleResponse response = new RoleResponse(entity);
        response.add(this.buildSelfLink(entity.getId()));
        return response;
    }

    public Link buildSelfLink(Integer id) {
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
