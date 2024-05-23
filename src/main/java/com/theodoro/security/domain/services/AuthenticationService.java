package com.theodoro.security.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodoro.security.api.rest.assemblers.AuthenticationAssembler;
import com.theodoro.security.api.rest.models.requests.AuthenticationRequest;
import com.theodoro.security.api.rest.models.responses.AuthenticationResponse;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.repositories.AccountRepository;
import com.theodoro.security.infra.securities.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ACCOUNT_EMAIL_NOT_FOUND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

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
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Account account = ((Account)auth.getPrincipal());

        Map<String, Object> claims = new HashMap<String, Object>();
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
            logger.info("//TODO error log");
            Account account = accountRepository.findByEmail(accountEmail).orElseThrow(() -> new NotFoundException(ACCOUNT_EMAIL_NOT_FOUND));
            if (jwtService.isTokenValid(refreshToken, account)) {
                String accessToken = jwtService.generateToken(account);

                AuthenticationResponse authResponse = authenticationAssembler.toEntity(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
