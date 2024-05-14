package com.theodoro.security.controllers;

import com.theodoro.security.assemblers.UserAssembler;
import com.theodoro.security.models.RoleEnum;
import com.theodoro.security.requests.AuthenticationRequest;
import com.theodoro.security.requests.RegisterRequest;
import com.theodoro.security.responses.AuthenticationResponse;
import com.theodoro.security.services.AuthenticationService;
import com.theodoro.security.services.RoleService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;
    private final RoleService roleService;
    private final UserAssembler userAssembler;

    public AuthenticationController(AuthenticationService service, RoleService roleService, UserAssembler userAssembler) {
        this.service = service;
        this.roleService = roleService;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws MessagingException {
        var roles = roleService.findByName(RoleEnum.USER.name()).orElseThrow(() -> new IllegalArgumentException("Role USER was not initialized"));
        var user = userAssembler.toEntity(request, roles);
        service.register(user, roles);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request){
        return ResponseEntity.ok(service.authentication(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/activate-account")
    public void confirmEmail(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }
}
