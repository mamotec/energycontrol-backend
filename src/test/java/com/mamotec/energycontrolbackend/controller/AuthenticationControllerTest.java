package com.mamotec.energycontrolbackend.controller;

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

    @MockBean
    private AuthenticationService authService;

    @Test
    public void shouldRegisterUser() throws Exception {

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

        mockMvc.perform(post("/auth/register").content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("2342424l2j4l23j32"));
    }

}