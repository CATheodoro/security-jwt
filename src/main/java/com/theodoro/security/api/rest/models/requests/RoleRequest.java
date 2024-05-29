package com.theodoro.security.api.rest.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class RoleRequest {

    @NotBlank(message = "Name is mandatory")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @JsonProperty("description")
    private String description;

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
}
