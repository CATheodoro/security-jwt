package com.theodoro.security.auth;

public final class AuthenticationResponseBuilder {
    private String accessToken;
    private String refreshToken;

    private AuthenticationResponseBuilder() {
    }

    public static AuthenticationResponseBuilder anAuthenticationResponse() {
        return new AuthenticationResponseBuilder();
    }

    public AuthenticationResponseBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public AuthenticationResponseBuilder refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AuthenticationResponse build() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(accessToken);
        authenticationResponse.setRefreshToken(refreshToken);
        return authenticationResponse;
    }
}
