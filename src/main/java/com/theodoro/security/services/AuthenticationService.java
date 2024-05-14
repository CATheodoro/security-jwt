package com.theodoro.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodoro.security.assemblers.AuthenticationAssembler;
import com.theodoro.security.infra.securities.JwtService;
import com.theodoro.security.models.Role;
import com.theodoro.security.models.Token;
import com.theodoro.security.models.User;
import com.theodoro.security.repositories.TokenRepository;
import com.theodoro.security.repositories.UserRepository;
import com.theodoro.security.requests.AuthenticationRequest;
import com.theodoro.security.responses.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationAssembler authenticationAssembler;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService, JwtService jwtService, AuthenticationManager authenticationManager, AuthenticationAssembler authenticationAssembler, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authenticationAssembler = authenticationAssembler;
        this.tokenService = tokenService;
    }

    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(User user, Role role) throws MessagingException {
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = tokenService.save(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());

        claims.put("name", user.getName());

        return authenticationAssembler.toEntity(jwtService.generateToken(claims, user),
                jwtService.generateRefreshToken(claims, user));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null){
            var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);

                var authResponse = authenticationAssembler.toEntity(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent");
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
