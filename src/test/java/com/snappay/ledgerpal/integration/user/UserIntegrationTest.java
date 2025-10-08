package com.snappay.ledgerpal.integration.user;

import com.snappay.ledgerpal.exception.UserRegistrationException;
import com.snappay.ledgerpal.model.UserModel;
import com.snappay.ledgerpal.model.operation.UserRegistrationModel;
import com.snappay.ledgerpal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testUserRegistration() {
        UserRegistrationModel registrationModel = new UserRegistrationModel();
        registrationModel.setUsername("Test");
        registrationModel.setEmail("test@test.com");
        registrationModel.setPassword("123");

        UserModel userModel = userService.register(registrationModel);
        assertThat(userModel.getUsername()).isEqualTo(registrationModel.getUsername());
        assertThat(userModel.getEmail()).isEqualTo(registrationModel.getEmail());
        assertThat(passwordEncoder.matches(registrationModel.getPassword(), userModel.getPassword())).isTrue();
    }

    @Test
    void testUserRegistrationDuplication() {
        UserRegistrationModel firstRegistrationModel = new UserRegistrationModel();
        firstRegistrationModel.setUsername("Test2");
        firstRegistrationModel.setEmail("new.test1@test.com");
        firstRegistrationModel.setPassword("123");

        userService.register(firstRegistrationModel);


        UserRegistrationModel secondRegistrationModel = new UserRegistrationModel();
        secondRegistrationModel.setUsername("Test2");
        secondRegistrationModel.setEmail("new.test2@test.com");
        secondRegistrationModel.setPassword("456");

        boolean failed = false;
        try {
            userService.register(secondRegistrationModel);
        } catch (UserRegistrationException e) {
            failed = true;
        }

        assertThat(failed).isTrue();
    }
}
