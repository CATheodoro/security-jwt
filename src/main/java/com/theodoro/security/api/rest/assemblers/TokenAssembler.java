package com.theodoro.security.api.rest.assemblers;

import com.theodoro.security.api.rest.controllers.TokenController;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.api.rest.models.responses.TokenResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TokenAssembler extends RepresentationModelAssemblerSupport<Token, TokenResponse> {

    public TokenAssembler() {
        super(TokenController.class, TokenResponse.class);
    }

    @Override
    public TokenResponse toModel(@NonNull Token entity) {
        TokenResponse response = new TokenResponse(entity);
        response.add(this.buildSelfLink(entity.getId()));
        return response;
    }

    public Link buildSelfLink(Integer id) {
        return linkTo(methodOn(TokenController.class).findById(id)).withSelfRel();
    }

    public Token toEntity(String generatedToken, Account account) {
        Token token = new Token();
        token.setToken(generatedToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        token.setAccount(account);
        return token;
    }

    public List<TokenResponse> toListModel(List<Token> tokens) {
        return tokens.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}