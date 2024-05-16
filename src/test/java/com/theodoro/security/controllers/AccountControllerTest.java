package com.theodoro.security.controllers;

import com.theodoro.security.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static com.theodoro.security.api.rest.controllers.AccountController.ACCOUNT_REGISTER_PATH;
import static com.theodoro.security.api.rest.controllers.AccountController.ACCOUNT_RESOURCE_PATH;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class AccountControllerTest extends ApplicationTests<AuthenticationControllerTest> {

    @Test
    public void shouldReturnCreatedWhenPostAuthenticationRegister() throws Exception {

        final String uri = fromPath(ACCOUNT_REGISTER_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnCreatedWhenPostAccountRegister");

        MvcResult result = mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, containsString(ACCOUNT_RESOURCE_PATH)))
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader(LOCATION))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Carlos T. Damasceno"))
                .andExpect(jsonPath("$.email").value("carlos@gmail.com"))
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$._links['self'].href").value(containsString(ACCOUNT_RESOURCE_PATH)));
    }
}