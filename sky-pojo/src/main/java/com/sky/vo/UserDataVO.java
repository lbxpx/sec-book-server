package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataVO {

    private Integer id;

    //用户名
    private String name;

    //大学
    private String university;

    //头像
    private String image;

    //身份
    private String role;

    //个人介绍
    private String introduction;

    //注册时间
    private String createAt;

    //密码
    private String password;

    //生日
    private String birth;

    //邮箱
    private String email;

    //性别
    private String sex;
}
