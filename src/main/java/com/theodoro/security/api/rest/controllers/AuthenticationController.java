package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.assemblers.AccountAssembler;
import com.theodoro.security.api.rest.models.requests.AuthenticationRequest;
import com.theodoro.security.api.rest.models.requests.RegisterRequest;
import com.theodoro.security.api.rest.models.responses.AuthenticationResponse;
import com.theodoro.security.domain.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthenticationController {

    public static final String AUTHENTICATION_RESOURCE_PATH = "/api/v1/auth";
    public static final String AUTHENTICATION_REGISTER_PATH = AUTHENTICATION_RESOURCE_PATH + "/register";
    public static final String AUTHENTICATION_AUTHENTICATE_PATH = AUTHENTICATION_RESOURCE_PATH + "/authenticate";
    public static final String AUTHENTICATION_REFRESH_TOKEN_PATH = AUTHENTICATION_RESOURCE_PATH + "refresh-token";

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping(AUTHENTICATION_AUTHENTICATE_PATH)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request){
        return ResponseEntity.ok(service.authentication(request));
    }

    @PostMapping(AUTHENTICATION_REFRESH_TOKEN_PATH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
