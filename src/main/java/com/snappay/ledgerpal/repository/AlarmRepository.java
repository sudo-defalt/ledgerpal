package com.snappay.ledgerpal.repository;

import com.snappay.ledgerpal.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
