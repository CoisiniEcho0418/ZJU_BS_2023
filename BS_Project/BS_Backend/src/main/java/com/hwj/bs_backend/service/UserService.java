package com.hwj.bs_backend.service;

import com.hwj.bs_backend.annotation.TokenRequired;
import com.hwj.bs_backend.mapper.DeviceMapper;
import com.hwj.bs_backend.param.DeviceAddRequest;
import com.hwj.bs_backend.param.LoginResponse;
import com.hwj.bs_backend.pojo.Result;
import com.hwj.bs_backend.pojo.User;
import com.hwj.bs_backend.mapper.UserMapper;
import com.hwj.bs_backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 处理用户登录的service方法
     *
     * @param username 登录输入的用户名
     * @param password 登录输入的密码
     * @return 登录结果的response类
     */
    public Result<LoginResponse> login(String username, String password) {
        try {
            // 检查用户是否存在
            User user = userMapper.findByUsername(username);
            if (user == null) {
                return Result.error("用户不存在");
            } else {
                // 验证密码
                if (!user.getPassword().equals(password)) {
                    return Result.error("密码错误");
                } else {
                    return createLoginResponse(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登录失败：" + e.getMessage());
        }
    }



    /**
     * 处理用户注册的service方法
     *
     * @param user 用注册时输入的用户信息构造的 User 类
     * @return 注册结果的response类（附代token，便于前端实现注册完毕直接跳转）
     */
    public Result<LoginResponse> register(User user) {
        try {
            // 判断email是否已经存在
            User existingEmail = userMapper.findByEmail(user.getEmail());
            if(existingEmail!=null){
                return Result.error("注册邮箱已存在！");
            }
            // 判断用户名是否已存在
            User existingUser = userMapper.findByUsername(user.getUsername());
            if (existingUser != null) {
                return Result.error("用户名已存在！");
            }

            // (TODO:判断其他业务逻辑，例如密码复杂度等)

            // 注册用户并返回用户信息及JWT令牌（调用registerUser方法时，会自动把自增id赋值给user对象）
            int insertRowCount = userMapper.registerUser(user);
            if (insertRowCount == 0) {
                return Result.error("新增用户失败！");
            }

            // 在用户注册成功后，自动帮用户注册device0001-device0005的设备（为了接受mqtt的消息）
            addDevicesForUser(user.getId());

            return createLoginResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    /**
     * 创建 LoginResponse 类的方法（在内部实现了创建token的逻辑）
     *
     * @param user 用于创建 LoginResponse 类的 User 类
     * @return 用user信息和token封装成的 LoginResponse 类
     */
    private Result<LoginResponse> createLoginResponse(User user) {
        String token = JwtUtil.sign(user.getId());

        // 构造 LoginResponse 对象
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setPhone(user.getPhone());
        loginResponse.setToken(token);

        // 返回成功结果，包含 LoginResponse 对象
        return Result.success(loginResponse);
    }

    /**
     * 处理用户修改密码的service方法
     *
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改密码结果的response类
     */
    // @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> updatePassword(String username, String oldPassword, String newPassword) {
        try {
            // 通过用户名查找用户，同时也返回密码以供校验
            User user = userMapper.findByUsername(username);
            if (user == null) {
                return Result.error("用户不存在");
            }

            System.out.println("原密码: "+user.getPassword()+" 传入的旧密码: "+oldPassword);
            // 验证旧密码是否正确
            if (!user.getPassword().equals(oldPassword)) {
                return Result.error("旧密码不正确");
            }

            // 更新密码
            int updateRowCount = userMapper.updatePassword(username, oldPassword, newPassword);

            if (updateRowCount > 0) {
                return Result.success("密码更新成功!");
            } else {
                return Result.error("密码更新失败!");
            }
        } catch (Exception e) {
            log.error("修改密码失败：" + e.getMessage());
            return Result.error("修改密码失败：" + e.getMessage());
        }
    }

    /**
     * 处理用户编辑个人信息的service方法
     *
     * @param userId      用户ID
     * @param newUsername 新用户名
     * @param newEmail    新邮箱
     * @param newPhone    新手机号
     * @return 编辑个人信息结果的response类
     */
    // @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> editUserInfo(Integer userId, String newUsername, String newEmail, String newPhone) {
        try {
            // 判断email是否已经存在
            User existingEmail = userMapper.findByEmail(newEmail);
            if(existingEmail!=null){
                return Result.error("新邮箱已存在！");
            }
            // 判断用户名是否已存在
            User existUser = userMapper.findByUsername(newUsername);
            if (existUser != null) {
                return Result.error("新用户名已存在！");
            }

            // 检查新用户名是否和现有用户名一致
            User existingUser = userMapper.findById(userId);

            if (existingUser != null) {
                if (Objects.equals(existingUser.getUsername(), newUsername)) {
                    return Result.error("新用户名与当前用户名相同!");
                } else if (Objects.equals(existingUser.getEmail(), newEmail)) {
                    return Result.error("新邮箱与当前邮箱相同!");
                } else if (Objects.equals(existingUser.getPhone(), newPhone)) {
                    return Result.error("新手机号与当前手机号相同!");
                }
            }
            assert existingUser != null;
            System.out.println(existingUser);

            // 更新用户信息
            int updateRowCount = userMapper.updateUserInfo(userId, newUsername, newEmail, newPhone);

            if (updateRowCount > 0) {
                return Result.success("个人信息编辑成功");
            } else {
                return Result.error("个人信息编辑失败或没有修改任何信息");
            }
        } catch (Exception e) {
            log.error("编辑个人信息失败：" + e.getMessage());
            return Result.error("编辑个人信息失败：" + e.getMessage());
        }
    }

    public User findUserById(Integer userId) {
        try {
            // Call the method in your UserMapper to find user by ID
            return userMapper.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            // handle exceptions or return null based on requirements
            return null;
        }
    }

    /**
     * 根据user_id重新获取用户信息
     *
     * @param userId 用户id
     * @return 登录结果的response类（存储用户信息和token）
     */
    public Result<LoginResponse> getUserInfo(Integer userId) {
        try {
            // 检查用户是否存在
            User user = userMapper.findById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            } else {
                    return createLoginResponse(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }

    /**
     * 在用户注册成功后新增设备的方法
     *
     * @param userId 新注册用户的ID
     */
    private void addDevicesForUser(Integer userId) {
        try {
            // 新增五个设备
            for (int i = 1; i <= 5; i++) {
                DeviceAddRequest device = new DeviceAddRequest();
                device.setUserId(userId);
                device.setDeviceName("device000" + i);
                device.setDeviceDescription("系统自动新增的设备");
                device.setDeviceType(new Random().nextInt(6) + 1);
                device.setIsActive(true);

                // 设置 registrationTime 和 lastUpdate（这个会在mapper中自动设成和registrationTime一致） 为当前时间
                Date currentTime = new Date();
                device.setRegistrationTime(currentTime);

                // 调用 deviceMapper 插入设备
                deviceMapper.insertDevice(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增设备失败：" + e.getMessage());
        }
    }
}
