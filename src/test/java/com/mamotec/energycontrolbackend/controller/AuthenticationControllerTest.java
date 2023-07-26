package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.auth.AuthenticationRequest;
import com.mamotec.energycontrolbackend.domain.auth.AuthenticationResponse;
import com.mamotec.energycontrolbackend.domain.auth.RegisterRequest;
import com.mamotec.energycontrolbackend.service.AuthenticationService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerTest extends BaseControllerTest {

    @Test
    public void shouldRegisterUser() throws Exception {
        // given
        final RegisterRequest request = RegisterRequest.builder()
                .firstName("MaMoTec")
                .lastName("Admin")
                .username("mvogt")
                .password("Start1234*")
                .email("test@test.de")
                .build();

        final AuthenticationResponse response = AuthenticationResponse.builder()
                .token("2342424l2j4l23j32")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        // then
        mockMvc.perform(post("/auth/register").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("2342424l2j4l23j32"));
    }

    @Test
    public void shouldAuthenticateUser() throws Exception {
        // given
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .username("mvogt")
                .password("Start1234*")
                .build();

        final AuthenticationResponse response = AuthenticationResponse.builder()
                .token("2342424l2j4l23j32")
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        // then
        mockMvc.perform(post("/auth/register").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("2342424l2j4l23j32"));
    }

}