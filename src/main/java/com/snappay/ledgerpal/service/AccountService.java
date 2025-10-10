package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.Transaction;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.exception.UserNotFoundException;
import com.snappay.ledgerpal.model.AccountModel;
import com.snappay.ledgerpal.model.operation.CreateAccountModel;
import com.snappay.ledgerpal.repository.AccountRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(MANDATORY)
    void modifyBalanceWithNewTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        long currentBalance = account.getBalance();
        currentBalance += transaction.getAmount();
        account.setBalance(currentBalance);
        accountRepository.save(account);
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

    @Transactional
    public AccountModel create(String username, CreateAccountModel model) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Account account = Account.builder()
                .uuid(UUID.randomUUID())
                .name(model.getName())
                .user(user).balance(0L).build();
        this.accountRepository.save(account);
        return AccountModel.build(account);
    }

    @Transactional
    public Page<AccountModel> getAll(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return accountRepository.findAllByUser(user, pageable).map(AccountModel::build);
    }

    @Transactional
    public Optional<AccountModel> getOne(String username, UUID uuid) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return accountRepository.findByUuidAndUser(uuid, user).map(AccountModel::build);
    }
}
