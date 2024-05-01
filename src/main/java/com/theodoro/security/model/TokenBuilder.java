package com.theodoro.security.model;

import java.time.LocalDateTime;

public final class TokenBuilder {
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    private User user;

    private TokenBuilder() {
    }

    public static TokenBuilder aToken() {
        return new TokenBuilder();
    }

    public TokenBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public TokenBuilder token(String token) {
        this.token = token;
        return this;
    }

    public TokenBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TokenBuilder expiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public TokenBuilder validatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
        return this;
    }

    public TokenBuilder user(User user) {
        this.user = user;
        return this;
    }

    public Token build() {
        Token token = new Token();
        token.setId(id);
        token.setToken(this.token);
        token.setCreatedAt(createdAt);
        token.setExpiresAt(expiresAt);
        token.setValidatedAt(validatedAt);
        token.setUser(user);
        return token;
    }
}
