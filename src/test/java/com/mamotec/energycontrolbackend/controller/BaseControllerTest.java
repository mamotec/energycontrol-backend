package com.mamotec.energycontrolbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamotec.energycontrolbackend.service.AuthenticationService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {AuthenticationController.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class BaseControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;
    @MockBean
    public AuthenticationService authService;
}
