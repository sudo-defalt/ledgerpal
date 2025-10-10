package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Category;
import com.snappay.ledgerpal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUuidAndUser(UUID uuid, User user);
    Page<Category> findAllByUser(User user, Pageable pageable);
}
