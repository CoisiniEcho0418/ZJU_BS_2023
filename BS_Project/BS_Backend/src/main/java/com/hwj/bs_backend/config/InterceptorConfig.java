package com.hwj.bs_backend.config;


import com.hwj.bs_backend.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by le.bai on 2020/4/18 21:03
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // InterceptorConfig内的addInterceptor需要一个实现HandlerInterceptor接口的拦截器实例，
        // addPathPatterns方法用于设置拦截器的过滤路径规则。
        // 拦截所有请求，通过判断是否有 @TokenRequired 注解 决定是否需要登录
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
}
