package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.Account;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountModel {
    private UUID uuid;
    private String name;
    private long balance;
    private UUID user;

    public static AccountModel build(Account account) {
        AccountModel model = new AccountModel();
        model.setUuid(account.getUuid());
        model.setName(account.getName());
        model.setBalance(account.getBalance());
        model.setUser(account.getUser().getUuid());
        return model;
    }
}
