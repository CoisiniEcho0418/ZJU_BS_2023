package com.hwj.bs_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 有参构造函数
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    
}

