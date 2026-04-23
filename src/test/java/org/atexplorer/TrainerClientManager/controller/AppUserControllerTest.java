package org.atexplorer.TrainerClientManager.controller;

import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tools.jackson.databind.json.JsonMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AppUserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private AppUserService appUserService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    // --- POST /api/accounts/client ---

    @Test
    void createClientAccount_validRequest_returns200() throws Exception {
        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(validClientRequest())))
                .andExpect(status().isOk());

        verify(appUserService).createClientAccount(any(CreateClientAccountDto.class));
    }

    @Test
    void createClientAccount_usernameTooShort_returns400() throws Exception {
        CreateClientAccountDto request = validClientRequest();
        request.setUsername("abc");

        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createClientAccount(any());
    }

    @Test
    void createClientAccount_passwordTooShort_returns400() throws Exception {
        CreateClientAccountDto request = validClientRequest();
        request.setPassword("abc");

        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createClientAccount(any());
    }

    @Test
    void createClientAccount_blankUsername_returns400() throws Exception {
        CreateClientAccountDto request = validClientRequest();
        request.setUsername("");

        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createClientAccount(any());
    }

    @Test
    void createClientAccount_invalidEmail_returns400() throws Exception {
        CreateClientAccountDto request = validClientRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createClientAccount(any());
    }

    @Test
    void createClientAccount_blankEmail_returns400() throws Exception {
        CreateClientAccountDto request = validClientRequest();
        request.setEmail("");

        mockMvc.perform(post("/api/accounts/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createClientAccount(any());
    }

    // --- POST /api/accounts/trainer ---

    @Test
    void createTrainerAccount_validRequest_returns200() throws Exception {
        mockMvc.perform(post("/api/accounts/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(validTrainerRequest())))
                .andExpect(status().isOk());

        verify(appUserService).createTrainerAccount(any(CreateTrainerAccountDto.class));
    }

    @Test
    void createTrainerAccount_usernameTooShort_returns400() throws Exception {
        CreateTrainerAccountDto request = validTrainerRequest();
        request.setUsername("abc");

        mockMvc.perform(post("/api/accounts/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createTrainerAccount(any());
    }

    @Test
    void createTrainerAccount_passwordTooShort_returns400() throws Exception {
        CreateTrainerAccountDto request = validTrainerRequest();
        request.setPassword("abc");

        mockMvc.perform(post("/api/accounts/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createTrainerAccount(any());
    }

    @Test
    void createTrainerAccount_invalidEmail_returns400() throws Exception {
        CreateTrainerAccountDto request = validTrainerRequest();
        request.setEmail("not-an-email");

        mockMvc.perform(post("/api/accounts/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createTrainerAccount(any());
    }

    @Test
    void createTrainerAccount_blankUsername_returns400() throws Exception {
        CreateTrainerAccountDto request = validTrainerRequest();
        request.setUsername("");

        mockMvc.perform(post("/api/accounts/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(appUserService, never()).createTrainerAccount(any());
    }

    // --- Helpers ---

    private CreateClientAccountDto validClientRequest() {
        CreateClientAccountDto dto = new CreateClientAccountDto();
        dto.setUsername("clientuser");
        dto.setPassword("password123");
        dto.setEmail("client@example.com");
        dto.setFirstName("Jane");
        dto.setLastName("Doe");
        dto.setGoals(new String[]{"Cardio"});
        return dto;
    }

    private CreateTrainerAccountDto validTrainerRequest() {
        CreateTrainerAccountDto dto = new CreateTrainerAccountDto();
        dto.setUsername("traineruser");
        dto.setPassword("password123");
        dto.setEmail("trainer@example.com");
        dto.setFirstName("John");
        dto.setLastName("Smith");
        dto.setCertifications(new String[]{"NASM"});
        return dto;
    }
}
