package com.theodoro.security.assemblers;

import com.theodoro.security.responses.AuthenticationResponse;
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