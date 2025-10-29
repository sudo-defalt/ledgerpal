package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.Alarm;
import lombok.Data;

import java.util.UUID;

@Data
public class AlarmModel {
    private UUID uuid;
    private String title;
    private long min;
    private long max;
    private UUID account;

    public static AlarmModel build(Alarm alarm) {
        AlarmModel model = new AlarmModel();
        model.uuid = alarm.getUuid();
        model.title = alarm.getTitle();
        model.min = alarm.getMin();
        model.max = alarm.getMax();
        model.account = alarm.getAccount().getUuid();
        return model;
    }
}
