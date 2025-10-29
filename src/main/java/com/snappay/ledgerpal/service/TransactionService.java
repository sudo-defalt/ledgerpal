package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.Category;
import com.snappay.ledgerpal.entity.Transaction;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.service.specification.TransactionSpecification;
import com.snappay.ledgerpal.exception.AccountNotFoundException;
import com.snappay.ledgerpal.exception.UserNotFoundException;
import com.snappay.ledgerpal.model.TransactionModel;
import com.snappay.ledgerpal.model.operation.CreateTransactionModel;
import com.snappay.ledgerpal.repository.CategoryRepository;
import com.snappay.ledgerpal.repository.TransactionRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public Page<TransactionModel> search(String username, Map<String, String> filter, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Specification<Transaction> specification = TransactionSpecification.build(user.getUuid(), filter);
        return transactionRepository.findAll(specification, pageable).map(TransactionModel::build);
    }

    @Transactional
    public TransactionModel create(String username, CreateTransactionModel model) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        Account account = accountService.findByUuidAndUser(model.getAccount(), user)
                .orElseThrow(AccountNotFoundException::new);
        Category category = categoryRepository.findByUuidAndUser(model.getCategory(), user)
                .orElse(null);

        Transaction transaction = new Transaction();
        transaction.setAmount(model.getAmount());
        transaction.setUuid(UUID.randomUUID());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDescription(model.getDescription());
        transaction.setCreatedAt(model.getTimestamp());

        transaction = transactionRepository.save(transaction);
        accountService.modifyBalanceWithNewTransaction(transaction);
        return TransactionModel.build(transaction);
    }
}
