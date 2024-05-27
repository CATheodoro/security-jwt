package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.models.requests.AuthenticationRequest;
import com.theodoro.security.api.rest.models.responses.AuthenticationResponse;
import com.theodoro.security.domain.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    public static final String AUTHENTICATION_RESOURCE_PATH = "/api/v1/auth";
    public static final String AUTHENTICATION_AUTHENTICATE_PATH = AUTHENTICATION_RESOURCE_PATH + "/authenticate";
    public static final String AUTHENTICATION_REFRESH_TOKEN_PATH = AUTHENTICATION_RESOURCE_PATH + "/refresh-token";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Authenticates in the system by returning a Token")
    @PostMapping(AUTHENTICATION_AUTHENTICATE_PATH)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authentication(request));
    }

    @Operation(summary = "Generate a new Token")
    @PostMapping(AUTHENTICATION_REFRESH_TOKEN_PATH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
