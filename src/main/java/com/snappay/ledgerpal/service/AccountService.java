package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.Transaction;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.exception.UserNotFoundException;
import com.snappay.ledgerpal.model.AccountModel;
import com.snappay.ledgerpal.repository.AccountRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static jakarta.transaction.Transactional.TxType.MANDATORY;


@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    Optional<Account> findByUuidAndUser(UUID uuid, User user) {
        return accountRepository.findByUuidAndUser(uuid, user);
    }

    @Transactional
    public Optional<Account> findByUsernameAndUuid(String username, UUID uuid) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return accountRepository.findByUuidAndUser(uuid, user);
    }

    @Transactional
    public AccountModel getDefault(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return AccountModel.build(accountRepository.findByNameAndUser(Account.DEFAULT, user));
    }

    @Transactional(MANDATORY)
    void modifyBalanceWithNewTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        long currentBalance = account.getBalance();
        currentBalance += transaction.getAmount();
        account.setBalance(currentBalance);
        accountRepository.save(account);
    }
}
