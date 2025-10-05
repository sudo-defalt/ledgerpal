package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
