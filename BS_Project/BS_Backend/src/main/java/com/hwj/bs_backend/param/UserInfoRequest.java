package com.hwj.bs_backend.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 有参构造函数
public class UserInfoRequest {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("new_username")
    private String newUsername;

    @JsonProperty("new_email")
    private String newEmail;

    @JsonProperty("new_phone")
    private String newPhone;

}

