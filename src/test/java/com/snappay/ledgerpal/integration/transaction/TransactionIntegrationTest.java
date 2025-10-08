package com.snappay.ledgerpal.integration.transaction;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.model.AccountModel;
import com.snappay.ledgerpal.model.TransactionModel;
import com.snappay.ledgerpal.model.operation.CreateTransactionModel;
import com.snappay.ledgerpal.model.operation.UserRegistrationModel;
import com.snappay.ledgerpal.service.AccountService;
import com.snappay.ledgerpal.service.TransactionService;
import com.snappay.ledgerpal.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @BeforeAll
    void setup() {
        UserRegistrationModel registrationModel = new UserRegistrationModel();
        registrationModel.setUsername("Transaction1");
        registrationModel.setEmail("Transaction1@test.com");
        registrationModel.setPassword("123");

        userService.register(registrationModel);
    }

    @Test
    void testTransactionCreation() {
        AccountModel defaultAccount = accountService.getDefault("Transaction1");
        CreateTransactionModel createTransactionModel = new CreateTransactionModel();
        createTransactionModel.setAmount(1_000L);
        createTransactionModel.setTimestamp(new Date());
        createTransactionModel.setDescription("I bought something wierd in subway..");
        createTransactionModel.setAccount(defaultAccount.getUuid());

        TransactionModel transactionModel = transactionService.create("Transaction1", createTransactionModel);

        Account account = accountService.findByUsernameAndUuid("Transaction1", defaultAccount.getUuid())
                .orElseThrow();

        assertThat(account.getBalance()).isEqualTo(transactionModel.getAmount());
    }
}
