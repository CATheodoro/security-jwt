package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.TokenAssembler;
import com.theodoro.security.api.rest.models.responses.TokenResponse;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.services.TokenService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TokenController {

    public static final String TOKEN_RESOURCE_PATH = "/api/v1/token";
    public static final String TOKEN_SELF_PATH = TOKEN_RESOURCE_PATH + "/{id}";

    private final TokenService tokenService;
    private final TokenAssembler tokenAssembler;

    public TokenController(TokenService tokenService, TokenAssembler tokenAssembler) {
        this.tokenService = tokenService;
        this.tokenAssembler = tokenAssembler;
    }

    @GetMapping(TOKEN_RESOURCE_PATH)
    public ResponseEntity<List<TokenResponse>> findAll() {
        var tokens = tokenService.findAll();
        return ResponseEntity.ok(tokenAssembler.toListModel(tokens));
    }

    @GetMapping(TOKEN_SELF_PATH)
    public ResponseEntity<TokenResponse> findById(@PathVariable("id") final Integer id) {
        Token token = tokenService.findById(id).orElseThrow(() -> new EntityNotFoundException("User ID was not Found"));
        return ResponseEntity.ok(tokenAssembler.toModel(token));
    }
}
