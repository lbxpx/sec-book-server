package com.sky.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDataDTO {
    //用户ID
    private Long userId;

    //用户名
    private String name;

    //大学
    private String university;

    //头像
    private String image;

    //个人介绍
    private String introduction;

    //密码
    private String password;

    //生日
    private Date birth;

    //邮箱
    private String email;

    //性别
    private String sex;
}
