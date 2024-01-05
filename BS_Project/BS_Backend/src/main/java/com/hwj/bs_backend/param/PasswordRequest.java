package com.hwj.bs_backend.param;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 有参构造函数
public class PasswordRequest {

    private String username;
    
    @JsonProperty("old_password")
    @JsonAlias("oldPassword") // 兼容其他名称
    private String oldPassword;

    @JsonProperty("new_password")
    @JsonAlias("newPassword") // 兼容其他名称
    private String newPassword;

}

