package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.TokenAssembler;
import com.theodoro.security.api.rest.models.responses.TokenResponse;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.TOKEN_NOT_FOUND;

@RestController
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

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
        List<Token> tokens = tokenService.findAll();
        return ResponseEntity.ok(tokenAssembler.toListModel(tokens));
    }

    @GetMapping(TOKEN_SELF_PATH)
    public ResponseEntity<TokenResponse> findById(@PathVariable("id") final String id) {
        Token token = tokenService.findById(id).orElseThrow(() ->{
            logger.info("Token with ID {} not found, cannot activate account.", id);
            return new NotFoundException(TOKEN_NOT_FOUND);
        });
        return ResponseEntity.ok(tokenAssembler.toModel(token));
    }
}
