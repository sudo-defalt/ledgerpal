package com.snappay.ledgerpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private long amount;

    @Column
    private String description;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne
    private Category category;
}
