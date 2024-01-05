package com.hwj.bs_backend.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCountResponse {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    // 总消息数量
    private int count;
    // 正常消息数量
    private int normalCount;
    // 非正常消息数量
    private int abnormalCount;
}

