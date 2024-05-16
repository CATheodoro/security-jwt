package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.AccountAssembler;
import com.theodoro.security.api.rest.models.requests.RegisterRequest;
import com.theodoro.security.api.rest.models.responses.AccountResponse;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    public static final String ACCOUNT_RESOURCE_PATH = "/api/v1/account";
    public static final String ACCOUNT_REGISTER_PATH = ACCOUNT_RESOURCE_PATH + "/register";
    public static final String ACCOUNT_SELF_PATH = ACCOUNT_RESOURCE_PATH + "/{id}";

    private final AccountService service;
    private final AccountAssembler accountAssembler;

    public AccountController(AccountService service, AccountAssembler accountAssembler) {
        this.service = service;
        this.accountAssembler = accountAssembler;
    }

    @PostMapping(ACCOUNT_REGISTER_PATH)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
        var account = accountAssembler.toEntity(request);

        var newAccount = service.register(account);
        return ResponseEntity.created(accountAssembler.buildSelfLink(newAccount.getId()).toUri()).build();
    }

    @GetMapping(ACCOUNT_RESOURCE_PATH)
    public ResponseEntity<List<AccountResponse>> findAll(){
        var accounts = service.findAll();
        return ResponseEntity.ok(accountAssembler.toListModel(accounts));
    }

    @GetMapping(ACCOUNT_SELF_PATH)
    public ResponseEntity<AccountResponse> findById(@PathVariable("id") final Integer id) {
        Account account = service.findById(id).orElseThrow(() -> new EntityNotFoundException("User ID was not Found"));
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}
