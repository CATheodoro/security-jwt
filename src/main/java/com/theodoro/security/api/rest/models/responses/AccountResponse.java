package com.theodoro.security.api.rest.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Role;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "roles"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "account", collectionRelation = "accounts")
public class AccountResponse extends RepresentationModel<AccountResponse> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("roles")
    private List<Role> roles;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.roles = account.getRoles();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static List<AccountResponse> convertResponse(List<Account> accounts) {
        return accounts.stream().map(AccountResponse::new).toList();
    }
}
