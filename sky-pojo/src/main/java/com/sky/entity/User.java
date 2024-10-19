package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //姓名
    private String username;

    //密码
    private String password;

    //邮箱
    private String email;

    //身份信息
    private String role;

    //更新时间
    private LocalDateTime updatedAt;

    //注册时间
    private LocalDateTime createdAt;

    //账号状态
    private String status;

    //用户头像
    private String image;
}
