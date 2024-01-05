package com.hwj.bs_backend;

import com.hwj.bs_backend.utils.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
@Import(JwtUtil.class)
public class BsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsBackendApplication.class, args);
    }

}
