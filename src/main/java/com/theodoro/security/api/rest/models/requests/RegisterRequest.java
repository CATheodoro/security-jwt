package com.theodoro.security.api.rest.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "Name is mandatory")
    @JsonProperty("name")
    private String name;

    @Email(message = "Email is not well formatted")
    @NotBlank(message = "Email is mandatory")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @JsonProperty("password")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
