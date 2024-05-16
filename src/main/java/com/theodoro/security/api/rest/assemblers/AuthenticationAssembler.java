package com.theodoro.security.api.rest.assemblers;

import com.theodoro.security.api.rest.models.responses.AuthenticationResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAssembler {

    public AuthenticationResponse toEntity(String generateToken, String generateRefreshToken) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(generateToken);
        response.setRefreshToken(generateRefreshToken);
        return response;
    }
}