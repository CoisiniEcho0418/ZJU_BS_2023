package com.hwj.bs_backend.controller;

import com.hwj.bs_backend.param.LoginRequest;
import com.hwj.bs_backend.param.LoginResponse;
import com.hwj.bs_backend.param.PasswordRequest;
import com.hwj.bs_backend.param.UserInfoRequest;
import com.hwj.bs_backend.pojo.*;
import com.hwj.bs_backend.service.UserService;
import com.hwj.bs_backend.annotation.TokenRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin // 允许跨域
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登录
     *
     * @param loginRequest 传入的用户实例信息（用一个封装类接受）
     * @return 返回service层处理后的Result实例
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    /**
     * 用户注册
     *
     * @param user 注册用户信息
     * @return 返回注册结果
     */
    @PostMapping("/register")
    public Result<LoginResponse> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户修改密码(TODO:还未实现token鉴权拦截)
     *
     * @param passwordRequest 包含用户名、旧密码、新密码的请求体
     * @return 返回修改密码结果
     */
    @PostMapping("/updatePassword")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> updatePassword(@RequestBody PasswordRequest passwordRequest) {
        return userService.updatePassword(passwordRequest.getUsername(), passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
    }

    /**
     * 编辑个人信息
     *
     * @param userInfoRequest 包含用户ID、新用户名、新邮箱和新手机号的请求体
     * @return 返回编辑个人信息结果
     */
    @PostMapping("/editUserInfo")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> editUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        System.out.println(userInfoRequest);
        return userService.editUserInfo(userInfoRequest.getUserId(), userInfoRequest.getNewUsername(), userInfoRequest.getNewEmail(), userInfoRequest.getNewPhone());
    }

    /**
     * 根据user_id重新获取用户信息
     *
     * @param userId 用户id
     * @return 登录结果的response类（存储用户信息和token）
     */
    @GetMapping("/getUserInfo")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<LoginResponse> login(@RequestParam("user_id") Integer userId) {
        return userService.getUserInfo(userId);
    }
}
