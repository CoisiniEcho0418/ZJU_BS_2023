package com.hwj.bs_backend.param;

// SearchCriteria.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSearchRequest {
    private Integer userId;
    private String deviceId;
    private String deviceName;
    private Integer deviceType;
    private Boolean isActive;
}

