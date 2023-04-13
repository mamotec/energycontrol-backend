package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByUsername() {
        // given
        User user = User.builder()
                .firstName("Moritz")
                .lastName("Vogt")
                .email("moritz.vogt@vogges.de")
                .username("mvogt")
                .password("testing")
                .build();
        userRepository.save(user);

        // when
        boolean exists = userRepository.findByUsername(user.getUsername())
                .isPresent();

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldNotFindByUsername() {
        // given
        var email = "nonexitstingemail@test.de";
        // when
        boolean exists = userRepository.findByUsername(email)
                .isPresent();

        // then
        assertThat(exists).isFalse();
    }
}