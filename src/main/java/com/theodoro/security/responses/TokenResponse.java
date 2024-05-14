package com.theodoro.security.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.models.Token;
import com.theodoro.security.models.User;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "token",
        "createdAt",
        "expiresAt",
        "validatedAt",
        "user"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "token", collectionRelation = "tokens")
public class TokenResponse extends RepresentationModel<TokenResponse> {

    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    private User user;

    public TokenResponse(Token token) {
        this.id = token.getId();
        this.token = token.getToken();
        this.createdAt = token.getCreatedAt();
        this.expiresAt = token.getExpiresAt();
        this.validatedAt = token.getValidatedAt();
        this.user = token.getUser();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
