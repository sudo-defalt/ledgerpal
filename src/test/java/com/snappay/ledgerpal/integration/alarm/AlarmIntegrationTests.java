package com.snappay.ledgerpal.integration.alarm;

import com.snappay.ledgerpal.model.AccountModel;
import com.snappay.ledgerpal.model.AlarmModel;
import com.snappay.ledgerpal.model.UserModel;
import com.snappay.ledgerpal.model.operation.CreateAlarmModel;
import com.snappay.ledgerpal.model.operation.UserRegistrationModel;
import com.snappay.ledgerpal.service.AccountService;
import com.snappay.ledgerpal.service.AlarmService;
import com.snappay.ledgerpal.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlarmIntegrationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AlarmService alarmService;

    private UserModel userModel;


    @BeforeAll
    void setup() {
        UserRegistrationModel registrationModel = new UserRegistrationModel();
        registrationModel.setUsername("Transaction1");
        registrationModel.setEmail("Transaction1@test.com");
        registrationModel.setPassword("123");

        this.userModel = userService.register(registrationModel);
    }

    @Test
    void testAlarmCreation() {
        AccountModel defaultAccount = accountService.getDefault(userModel.getUsername());
        CreateAlarmModel createAlarmModel = new CreateAlarmModel();
        createAlarmModel.setAccount(defaultAccount.getUuid());
        createAlarmModel.setTitle("My Alarm");
        createAlarmModel.setMin(1000);
        createAlarmModel.setMax(10000);

        AlarmModel alarm = alarmService.create(userModel.getUsername(), createAlarmModel);
        assertThat(alarm.getTitle()).isEqualTo(createAlarmModel.getTitle());
    }


}
