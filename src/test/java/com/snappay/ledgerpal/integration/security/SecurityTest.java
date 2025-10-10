package com.snappay.ledgerpal.integration.security;

import com.snappay.ledgerpal.model.operation.UserRegistrationModel;
import com.snappay.ledgerpal.security.jwt.JwtCredential;
import com.snappay.ledgerpal.security.jwt.JwtCredentialService;
import com.snappay.ledgerpal.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SecurityTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtCredentialService jwtCredentialService;

    @BeforeAll
    void setup() {
        UserRegistrationModel registrationModel = new UserRegistrationModel();
        registrationModel.setUsername("Test");
        registrationModel.setEmail("test@test.com");
        registrationModel.setPassword("123");
        userService.register(registrationModel);
    }

    @Test
    void testTokenGeneration() {
        UserDetails user = userDetailsService.loadUserByUsername("Test");
        JwtCredential jwtCredential = jwtCredentialService.generate(user);
        assertThat(jwtCredentialService.validate(jwtCredential.accessToken())).isTrue();
        assertThat(jwtCredentialService.validate(jwtCredential.refreshToken())).isTrue();
    }

    @Test
    void testSubjectValidity() {
        UserDetails user = userDetailsService.loadUserByUsername("Test");
        JwtCredential jwtCredential = jwtCredentialService.generate(user);

        String accessedToken = jwtCredential.accessToken();
        String refreshToken = jwtCredential.refreshToken();
        assertThat(jwtCredentialService.extractUser(accessedToken).getUsername()).isEqualTo(user.getUsername());
        assertThat(jwtCredentialService.extractUser(refreshToken).getUsername()).isEqualTo(user.getUsername());
    }
}
