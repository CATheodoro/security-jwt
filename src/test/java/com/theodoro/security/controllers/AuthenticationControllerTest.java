package com.theodoro.security.controllers;

import com.jayway.jsonpath.JsonPath;
import com.theodoro.security.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.theodoro.security.api.rest.controllers.AuthenticationController.AUTHENTICATION_AUTHENTICATE_PATH;
import static com.theodoro.security.api.rest.controllers.AuthenticationController.AUTHENTICATION_REFRESH_TOKEN_PATH;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ACCOUNT_ID_NOT_FOUND;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.BAD_CREDENTIALS;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class AuthenticationControllerTest extends ApplicationTests<AuthenticationControllerTest> {

    @Test
    public void shouldReturnOkWhenPostAuthenticationAuthenticate() throws Exception {

        final String uri = fromPath(AUTHENTICATION_AUTHENTICATE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnOkWhenPostAuthenticationAuthenticate");

        MvcResult result = mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenPostWithWrongPassword() throws Exception {

        final String uri = fromPath(AUTHENTICATION_AUTHENTICATE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnBadRequestWhenPostWithWrongPassword");

        mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(BAD_CREDENTIALS.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(BAD_CREDENTIALS.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnOkWhenRefreshTokenIsValid() throws Exception {

        final String uri = fromPath(AUTHENTICATION_AUTHENTICATE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnOkWhenPostAuthenticationAuthenticate");

        MvcResult result = mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andReturn();

        //Refresh Token
        final String uriRefresh = fromPath(AUTHENTICATION_REFRESH_TOKEN_PATH).toUriString();
        String responseContent = result.getResponse().getContentAsString();

        String refreshToken = JsonPath.read(responseContent, "$.refreshToken");

        String authHeader = "Bearer " + refreshToken;

        mockMvc.perform(post(uriRefresh)
                        .header(AUTHORIZATION, authHeader))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    public void shouldReturnBadRequestWhenRefreshTokenIsExpired() throws Exception {

        final String uri = fromPath(AUTHENTICATION_REFRESH_TOKEN_PATH).toUriString();

        String authHeader = "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiQ2FybG9zIEFsZXhhbmRyZSBUaGVvZG9ybyBEYW1hc2Nlbm8iLCJzdWIiOiJzZW5oYTEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MTU2MzQ5MjUsImV4cCI6MTcxNTcyMTMyNSwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdfQ.MDZxvR-pLTTWbihLaMxjUYVgOMu-E2vr-RL8BXFCncJ2sC00TxCz9JwGPsDayEhiAEpEVr2NJqM_jt1vt4jNeA";

        mockMvc.perform(post(uri)
                    .header(AUTHORIZATION, authHeader))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(ACCOUNT_ID_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(ACCOUNT_ID_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

}