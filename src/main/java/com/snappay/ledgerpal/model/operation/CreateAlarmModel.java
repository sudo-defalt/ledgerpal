package com.snappay.ledgerpal.model.operation;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateAlarmModel {
    private String title;
    private long min;
    private long max;
    private UUID account;
}
