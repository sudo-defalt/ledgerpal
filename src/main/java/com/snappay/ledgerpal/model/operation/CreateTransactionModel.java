package com.snappay.ledgerpal.model.operation;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateTransactionModel {
    private long amount;
    private Date timestamp = new Date();
    private String description;
    private UUID account;
    private UUID category;
}
