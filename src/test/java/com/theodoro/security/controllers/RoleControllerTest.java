package com.theodoro.security.controllers;

import com.theodoro.security.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static com.theodoro.security.api.rest.controllers.RoleController.ROLE_RESOURCE_PATH;
import static com.theodoro.security.api.rest.controllers.RoleController.ROLE_SELF_PATH;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ROLE_ALREADY_EXISTS;
import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ROLE_ID_NOT_FOUND;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class RoleControllerTest extends ApplicationTests<RoleControllerTest> {

    @Test
    public void shouldReturnOkWhenGetRoleFindAll() throws Exception {

        final String uri = fromPath(ROLE_RESOURCE_PATH).toUriString();

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("81803ca6-88bb-421b-a220-90b8c7fe5948"))
                .andExpect(jsonPath("$[0].name").value("USER"))
                .andExpect(jsonPath("$[0].description").value("Pouca permissão"))
                .andExpect(jsonPath("$[0].createdDate").exists())
                .andExpect(jsonPath("$[0].links[0].href").value(containsString(uri)))

                .andExpect(jsonPath("$[1].id").value("d4e46efb-218b-450e-bc9e-6e79ed05f7c7"))
                .andExpect(jsonPath("$[1].name").value("ADMIN"))
                .andExpect(jsonPath("$[1].description").value("Permissão para tudo"))
                .andExpect(jsonPath("$[1].createdDate").exists())
                .andExpect(jsonPath("$[1].links[0].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnCreatedWhenPostRoleSave() throws Exception {

        final String uri = fromPath(ROLE_RESOURCE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnCreatedWhenPostRoleSave");

        MvcResult result = mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, containsString(uri)))
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader(LOCATION))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("USER_PLUS"))
                .andExpect(jsonPath("$.description").value("Pouca permissão. Acesso extras"))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnConflictWhenPostRoleAlreadyExists() throws Exception {

        final String uri = fromPath(ROLE_RESOURCE_PATH).toUriString();

        String content = super.getScenarioBody("shouldReturnConflictWhenPostRoleAlreadyExists");

        mockMvc.perform(post(uri)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(ROLE_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(ROLE_ALREADY_EXISTS.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnOkWhenGetRoleFindById() throws Exception {

        final String uri = fromPath(ROLE_SELF_PATH).buildAndExpand("81803ca6-88bb-421b-a220-90b8c7fe5948").toUriString();

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("81803ca6-88bb-421b-a220-90b8c7fe5948"))
                .andExpect(jsonPath("$.name").value("USER"))
                .andExpect(jsonPath("$.description").value("Pouca permissão"))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }

    @Test
    public void shouldReturnNotFoundWhenGetRoleFindById() throws Exception {

        final String uri = fromPath(ROLE_SELF_PATH).buildAndExpand("-1").toUriString();

        mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.messages").exists())
                .andExpect(jsonPath("$.errors.messages[0].code").value(ROLE_ID_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.errors.messages[0].message").value(ROLE_ID_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$._links['self'].href").value(containsString(uri)));
    }
}
