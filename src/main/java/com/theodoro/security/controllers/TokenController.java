package com.theodoro.security.controllers;

import com.theodoro.security.assemblers.TokenAssembler;
import com.theodoro.security.models.Token;
import com.theodoro.security.responses.TokenResponse;
import com.theodoro.security.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenService tokenService;
    private final TokenAssembler tokenAssembler;

    public TokenController(TokenService tokenService, TokenAssembler tokenAssembler) {
        this.tokenService = tokenService;
        this.tokenAssembler = tokenAssembler;
    }

    @GetMapping
    public ResponseEntity<List<TokenResponse>> findAll() {
        var tokens = tokenService.findAll();
        return ResponseEntity.ok(tokenAssembler.toListModel(tokens));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenResponse> findById(@PathVariable("id") final Integer id) {
        Token token = tokenService.findById(id).orElseThrow(() -> new IllegalArgumentException("User ID was not Found"));
        return ResponseEntity.ok(tokenAssembler.toModel(token));
    }
}
