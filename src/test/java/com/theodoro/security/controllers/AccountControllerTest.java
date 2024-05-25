package com.theodoro.security.controllers;

import com.theodoro.security.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static com.theodoro.security.api.rest.controllers.AccountController.*;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ACCOUNT_ID_NOT_FOUND;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.USER_ALREADY_EXISTS;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class AccountControllerTest extends ApplicationTests<AuthenticationControllerTest> {

    @Test
    public void shouldReturnCreatedWhenPostAccountRegister() throws Exception {

        final String uri = fromPath(ACCOUNT_REGISTER_PATH).toUriString();
        final String accountUri = fromPath(ACCOUNT_RESOURCE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnCreatedWhenPostAccountRegister");

        MvcResult result = mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, containsString(accountUri)))
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader(LOCATION))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Carlos T. Damasceno"))
                .andExpect(jsonPath("$.email").value("carlos@gmail.com"))
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$._links['self'].href").value(containsString(accountUri)));
    }

    @Test
    public void shouldReturnConflictWhenPostAccountRegisterWithAlreadyEmailRegistered() throws Exception {

        final String uri = fromPath(ACCOUNT_REGISTER_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnConflictWhenPostAuthenticationRegisterWithAlreadyEmailRegistered");

        mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(USER_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(USER_ALREADY_EXISTS.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnOkWhenGetAccountFindAll() throws Exception {

        final String uri = fromPath(ACCOUNT_RESOURCE_PATH).toUriString();

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e8511df9-43a7-4384-90b1-bb9da544db34"))
                .andExpect(jsonPath("$[0].name").value("Ruan Felipe"))
                .andExpect(jsonPath("$[0].email").value("ruan@gmail.com"))
                .andExpect(jsonPath("$[0].roles[0].name").value("USER"))
                .andExpect(jsonPath("$[0].links[0].href").value(containsString(uri)))

                .andExpect(jsonPath("$[1].id").value("81803ca6-88bb-421b-a220-90b8c7fe5948"))
                .andExpect(jsonPath("$[1].name").value("Vitor Hugo"))
                .andExpect(jsonPath("$[1].email").value("vitor@gmail.com"))
                .andExpect(jsonPath("$[1].roles[0].name").value("USER"))
                .andExpect(jsonPath("$[1].links[0].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnOkWhenGetAccountWithExistId() throws Exception {

        final String uri = fromPath(ACCOUNT_SELF_PATH).buildAndExpand("e8511df9-43a7-4384-90b1-bb9da544db34").toUriString();

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Ruan Felipe"))
                .andExpect(jsonPath("$.email").value("ruan@gmail.com"))
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$._links['self'].href").value(containsString(ACCOUNT_RESOURCE_PATH)));
    }

    @Test
    public void shouldReturnNotFoundWhenGetAccountWithNotExistId() throws Exception {

        final String uri = fromPath(ACCOUNT_SELF_PATH).buildAndExpand(-1).toUriString();

        mockMvc.perform(get(uri))
                    .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(ACCOUNT_ID_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(ACCOUNT_ID_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }
}