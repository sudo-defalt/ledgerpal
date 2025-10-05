package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.Category;
import com.snappay.ledgerpal.entity.Transaction;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.model.TransactionModel;
import com.snappay.ledgerpal.model.operation.CreateTransactionModel;
import com.snappay.ledgerpal.repository.AccountRepository;
import com.snappay.ledgerpal.repository.CategoryRepository;
import com.snappay.ledgerpal.repository.TransactionRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionModel create(String username, CreateTransactionModel model) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        Account account = accountRepository.findByUuidAndUser(model.getAccount(), user)
                .orElseThrow();
        Category category = categoryRepository.findByUuidAndUser(model.getCategory(), user)
                // transaction category is optional
                .orElse(null);

        Transaction transaction = new Transaction();
        transaction.setAmount(model.getAmount());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setDescription(model.getDescription());
        transaction.setCreatedAt(model.getTimestamp());

        transaction = transactionRepository.save(transaction);
        return TransactionModel.build(transaction);
    }
}
