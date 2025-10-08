package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.Category;
import com.snappay.ledgerpal.entity.Transaction;
import lombok.Data;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
public class TransactionModel {
    private UUID uuid;
    private long amount;
    private Date createdAt;
    private String description;
    private UUID account;
    private UUID category;

    public static TransactionModel build(Transaction transaction) {
        TransactionModel model = new TransactionModel();
        model.setUuid(transaction.getUuid());
        model.setAmount(transaction.getAmount());
        model.setCreatedAt(transaction.getCreatedAt());
        model.setAccount(transaction.getAccount().getUuid());
        Optional.ofNullable(transaction.getCategory()).map(Category::getUuid).ifPresent(model::setCategory);
        return model;
    }
}
