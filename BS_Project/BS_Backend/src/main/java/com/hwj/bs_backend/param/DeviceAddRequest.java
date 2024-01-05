package com.hwj.bs_backend.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAddRequest {
    // 使用 @JsonProperty 注解来指定请求体中的参数与实体类字段之间的映射关系
    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("device_name")
    private String deviceName;

    @JsonProperty("device_type")
    private int deviceType;

    @JsonProperty("device_description")
    private String deviceDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("registration_time")
    private Date registrationTime;

    @JsonProperty("is_active")
    private Boolean isActive;
}

