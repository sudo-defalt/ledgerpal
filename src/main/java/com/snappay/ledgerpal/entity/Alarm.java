package com.snappay.ledgerpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "alarms")
public class Alarm {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    private Account account;

    @Column(nullable = false)
    private long min;

    @Column(nullable = false)
    private long max;

    public boolean doesTriggerOn(Transaction transaction) {
        long resultingBalance = account.getBalance() + transaction.getAmount();
        return min < resultingBalance && resultingBalance < max;
    }
}
