package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.AccountAssembler;
import com.theodoro.security.api.rest.models.requests.RegisterRequest;
import com.theodoro.security.api.rest.models.responses.AccountResponse;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Role;
import com.theodoro.security.domain.enumeration.RoleEnum;
import com.theodoro.security.domain.exceptions.ConflictException;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.services.AccountService;
import com.theodoro.security.domain.services.RoleService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.*;

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    public static final String ACCOUNT_RESOURCE_PATH = "/api/v1/account";
    public static final String ACCOUNT_REGISTER_PATH = ACCOUNT_RESOURCE_PATH + "/register";
    public static final String ACCOUNT_SELF_PATH = ACCOUNT_RESOURCE_PATH + "/{id}";
    public static final String ACCOUNT_CHANGE_PASSWORD_PATH = ACCOUNT_RESOURCE_PATH + "/change-password";

    private final AccountService accountservice;
    private final AccountAssembler accountAssembler;
    private final RoleService roleService;

    public AccountController(AccountService accountservice, AccountAssembler accountAssembler, RoleService roleService) {
        this.accountservice = accountservice;
        this.accountAssembler = accountAssembler;
        this.roleService = roleService;
    }

    @PostMapping(ACCOUNT_REGISTER_PATH)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
        accountservice.findByEmail(request.getEmail()).ifPresent(search -> {
            throw new ConflictException(USER_ALREADY_EXISTS,
                    accountAssembler.buildSelfLink(search.getId()).toUri());
        });
        Role roles = roleService.findByName(RoleEnum.USER.name()).orElseThrow(() ->
                new NotFoundException(ROLE_NOT_INITIALIZED_NOT_FOUND));

        Account account = accountAssembler.toEntity(request, roles);
        Account newAccount = accountservice.register(account);

        return ResponseEntity.created(accountAssembler.buildSelfLink(newAccount.getId()).toUri()).build();
    }

    @GetMapping(ACCOUNT_RESOURCE_PATH)
    public ResponseEntity<List<AccountResponse>> findAll(){
        List<Account> accounts = accountservice.findAll();
        return ResponseEntity.ok(accountAssembler.toListModel(accounts));
    }

    @GetMapping(ACCOUNT_SELF_PATH)
    public ResponseEntity<AccountResponse> findById(@PathVariable("id") final String id) {

        Account account = accountservice.findById(id).orElseThrow(() -> {
            logger.info("User account not found for id: {}", id);
            return new NotFoundException(ACCOUNT_ID_NOT_FOUND);
        });
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}
