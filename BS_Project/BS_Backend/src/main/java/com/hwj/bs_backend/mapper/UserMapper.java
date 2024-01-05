package com.hwj.bs_backend.mapper;

import com.hwj.bs_backend.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 函数中的Param注解可以不用（只有在SpringBoot1.x版本或者单独使用Mybatis时才需要）
    // 如果查询的表字段与实体类的字段一致（驼峰对下划线可以视为一致），通常情况下不需要额外的 @Results 注解。
    // MyBatis 会自动将查询结果映射到实体类的字段，前提是实体类的字段名与表字段名一致。（下面某几个接口的Result注解其实可以删去）

    // 注册用户并返回自动生成的用户ID，返回的结果是新增的行数
    @Insert("INSERT INTO user (username, password, email, phone) VALUES (#{user.username}, #{user.password}, #{user.email}, #{user.phone})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    //使用@Options注解配置自动生成主键，以便获取自动生成的用户ID
    int registerUser(@Param("user") User user);

    // 通过用户名查找用户，同时也返回密码以供校验
    @Select("SELECT id, username, password, email, phone FROM user WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone")
    })
    User findByUsername(@Param("username") String username);

    // 通过email查找用户
    @Select("SELECT id, username, password, email, phone FROM user WHERE email = #{email}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone")
    })
    User findByEmail(@Param("email") String email);

    // 更新用户密码
    @Update("UPDATE user SET password = #{newPassword} WHERE username = #{username} AND password = #{oldPassword}")
    int updatePassword(@Param("username") String username, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    // 更新用户信息（包括用户名、邮箱和手机号）
    @Update({
            "<script>",
            "UPDATE user",
            "<set>",
            "<if test='newUsername != null'>",
            "username = #{newUsername},",
            "</if>",
            "<if test='newEmail != null'>",
            "email = #{newEmail},",
            "</if>",
            "<if test='newPhone != null'>",
            "phone = #{newPhone}",
            "</if>",
            "</set>",
            "WHERE id = #{userId}",
            "</script>"
    })
    int updateUserInfo(@Param("userId") Integer userId, @Param("newUsername") String newUsername, @Param("newEmail") String newEmail, @Param("newPhone") String newPhone);

    // 根据userId来查找用户
    @Select("SELECT id, username, password, email, phone FROM user WHERE id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone")
    })
    User findById(@Param("userId") Integer userId);
}
