package com.theodoro.security.api.rest.assemblers;

import com.theodoro.security.api.rest.controllers.AccountController;
import com.theodoro.security.api.rest.models.requests.RegisterRequest;
import com.theodoro.security.api.rest.models.responses.AccountResponse;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Role;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountAssembler extends RepresentationModelAssemblerSupport<Account, AccountResponse> {

    private final PasswordEncoder passwordEncoder;

    public AccountAssembler(PasswordEncoder passwordEncoder) {
        super(AccountController.class, AccountResponse.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountResponse toModel(@NonNull Account entity) {
        AccountResponse response = new AccountResponse(entity);
        response.add(this.buildSelfLink(response.getId()));
        return response;
    }

    public Link buildSelfLink(String id) {
        return linkTo(methodOn(AccountController.class).findById(id)).withSelfRel();
    }

    public Account toEntity(RegisterRequest request, Role role) {
        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoles(List.of(role));
        account.setAccountLocked(false);
        account.setEnabled(false);
        return account;
    }

    public List<AccountResponse> toListModel(List<Account> accounts) {
        return accounts.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
