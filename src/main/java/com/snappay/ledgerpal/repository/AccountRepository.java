package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUuidAndUser(UUID uuid, User user);
}
