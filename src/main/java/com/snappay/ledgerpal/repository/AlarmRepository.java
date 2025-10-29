package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Alarm;
import com.snappay.ledgerpal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("select al from Alarm al join fetch al.account ac where ac.user = :user")
    Page<Alarm> findAllByUser(User user, Pageable pageable);
}
