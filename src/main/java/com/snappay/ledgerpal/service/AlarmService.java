package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Account;
import com.snappay.ledgerpal.entity.Alarm;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.exception.AccountNotFoundException;
import com.snappay.ledgerpal.exception.UserNotFoundException;
import com.snappay.ledgerpal.model.AlarmModel;
import com.snappay.ledgerpal.model.operation.CreateAlarmModel;
import com.snappay.ledgerpal.repository.AccountRepository;
import com.snappay.ledgerpal.repository.AlarmRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AlarmService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public AlarmModel create(String username, CreateAlarmModel model) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Account account = accountRepository.findByUuidAndUser(model.getAccount(), user)
                .orElseThrow(AccountNotFoundException::new);
        Alarm alarm = Alarm.builder()
                .uuid(UUID.randomUUID())
                .account(account)
                .title(model.getTitle())
                .max(model.getMax()).min(model.getMin())
                .build();
        alarm = alarmRepository.save(alarm);
        return AlarmModel.build(alarm);
    }

    @Transactional
    public Page<AlarmModel> getAll(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return alarmRepository.findAllByUser(user, pageable).map(AlarmModel::build);
    }
}
