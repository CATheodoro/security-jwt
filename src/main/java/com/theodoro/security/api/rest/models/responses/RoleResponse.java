package com.theodoro.security.api.rest.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.domain.entities.Role;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "createdDate",
        "lastModifiedDate"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "role", collectionRelation = "roles")
public class RoleResponse extends RepresentationModel<RoleResponse> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createdDate")
    private LocalDateTime createdDate;

    @JsonProperty("lastModifiedDate")
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