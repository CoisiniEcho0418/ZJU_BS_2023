package com.hwj.bs_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqttMessageData {
    private int alert;
    private String clientId;
    private String info;
    private double lat;
    private double lng;
    private long timestamp;
    private int value;
}
