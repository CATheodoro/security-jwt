package com.theodoro.security.api.rest.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.entities.Account;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "token",
        "createdAt",
        "expiresAt",
        "validatedAt",
        "account"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "token", collectionRelation = "tokens")
public class TokenResponse extends RepresentationModel<TokenResponse> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("token")
    private String token;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("expiresAt")
    private LocalDateTime expiresAt;

    @JsonProperty("validatedAt")
    private LocalDateTime validatedAt;

    @JsonProperty("account")
    private Account account;

    public TokenResponse(Token token) {
        this.id = token.getId();
        this.token = token.getToken();
        this.createdAt = token.getCreatedAt();
        this.expiresAt = token.getExpiresAt();
        this.validatedAt = token.getValidatedAt();
        this.account = token.getAccount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
