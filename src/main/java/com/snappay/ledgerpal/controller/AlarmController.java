package com.snappay.ledgerpal.controller;

import com.snappay.ledgerpal.model.AlarmModel;
import com.snappay.ledgerpal.model.operation.CreateAlarmModel;
import com.snappay.ledgerpal.service.AlarmService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/alarms")
public class AlarmController {
    private AlarmService alarmService;

    @PostMapping
    public AlarmModel create(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestBody CreateAlarmModel model) {
        return alarmService.create(userDetails.getUsername(), model);
    }

    @GetMapping
    public PagedModel<AlarmModel> getAll(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return new PagedModel<>(alarmService.getAll(userDetails.getUsername(), pageable));
    }
}
