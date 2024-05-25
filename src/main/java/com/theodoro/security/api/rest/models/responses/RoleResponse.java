package com.theodoro.security.api.rest.models.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.domain.entities.Role;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "createdDate",
        "lastModifiedDate"
})
public class RoleResponse extends RepresentationModel<TokenResponse> {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.createdDate = role.getCreatedDate();
        this.lastModifiedDate = role.getLastModifiedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}