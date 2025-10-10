package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findAllByUser(User user, Pageable pageable);
    Optional<Account> findByUuidAndUser(UUID uuid, User user);
    Account findByNameAndUser(String name, User user);
}
