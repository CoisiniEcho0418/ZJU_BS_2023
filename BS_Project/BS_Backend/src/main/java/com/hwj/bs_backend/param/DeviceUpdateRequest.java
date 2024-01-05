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
public class DeviceUpdateRequest {
    @JsonProperty("device_name")
    private String deviceName;

    /*
    后端最好用封装类代替基本类来定义接受请求体参数的变量，因为基本类可能无法保存Null值，
    会将Null存为默认值，从而对动态sql的判断条件产生影响，比如这里用int存的话，如果前端传入null或者undefined
    它就会变成默认值0，影响后面动态sql非null的判断
     */
    @JsonProperty("device_type")
    private Integer deviceType;

    @JsonProperty("device_description")
    private String deviceDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("last_update")
    private Date lastUpdate;

    @JsonProperty("is_active")
    private Boolean isActive;
}

