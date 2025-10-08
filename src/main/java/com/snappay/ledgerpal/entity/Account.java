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
@Table(name = "accounts")
public class Account {
    public static final String DEFAULT = "default";

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column
    private String name;

    @Column
    private long balance;

    @ManyToOne(optional = false)
    private User user;
}
