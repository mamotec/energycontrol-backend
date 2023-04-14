package com.mamotec.energycontrolbackend.service;

import com.mamotec.energycontrolbackend.domain.auth.AuthenticationRequest;
import com.mamotec.energycontrolbackend.domain.auth.AuthenticationResponse;
import com.mamotec.energycontrolbackend.domain.auth.RegisterRequest;
import com.mamotec.energycontrolbackend.domain.user.User;
import com.mamotec.energycontrolbackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthenticationServiceIntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        registerRequest = new RegisterRequest("John", "Doe", "john.doe", "john", "password");
        authenticationRequest = new AuthenticationRequest("john", "password");
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() {
        // given/when
        AuthenticationResponse registerResponse = authenticationService.register(registerRequest);

        //then
        assertThat(registerResponse).isNotNull();
        assertThat(registerResponse.getToken()).isNotBlank();
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(userOptional).isPresent();
        User user = userOptional.get();
        assertThat(user.getEmail()).isEqualTo(registerRequest.getEmail());

    }

    @Test
    void shouldNotRegisterUserWithDuplicateUsername() {
        // given/when
        authenticationService.register(registerRequest);

        //then
        assertThrows(DataIntegrityViolationException.class, () -> authenticationService.register(registerRequest));
    }

    @Test
    void shouldAuthUser() {
        // given/when
        authenticationService.register(registerRequest);
        AuthenticationResponse authResponse = authenticationService.authenticate(authenticationRequest);

        //then
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getToken()).isNotBlank();
    }

    @Test
    void shouldNotAuthUserWithWrongPassword() {
        // given/when
        authenticationService.register(registerRequest);
        authenticationRequest.setPassword("wef");

        //then
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(authenticationRequest));
    }
}
