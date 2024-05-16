package com.theodoro.security.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodoro.security.api.rest.assemblers.AuthenticationAssembler;
import com.theodoro.security.api.rest.models.requests.AuthenticationRequest;
import com.theodoro.security.api.rest.models.responses.AuthenticationResponse;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.repositories.AccountRepository;
import com.theodoro.security.infra.securities.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationAssembler authenticationAssembler;

    public AuthenticationService(AccountRepository accountRepository, JwtService jwtService, AuthenticationManager authenticationManager, AuthenticationAssembler authenticationAssembler) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authenticationAssembler = authenticationAssembler;
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var account = ((Account)auth.getPrincipal());

        claims.put("name", account.getName());

        return authenticationAssembler.toEntity(jwtService.generateToken(claims, account),
                jwtService.generateRefreshToken(claims, account));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String accountEmail = jwtService.extractUsername(refreshToken);
        if (accountEmail != null){
            var account = accountRepository.findByEmail(accountEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (jwtService.isTokenValid(refreshToken, account)) {
                var accessToken = jwtService.generateToken(account);

                var authResponse = authenticationAssembler.toEntity(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
