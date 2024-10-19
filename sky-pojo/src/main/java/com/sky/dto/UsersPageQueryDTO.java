package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersPageQueryDTO implements Serializable {

    //员工姓名
    private String username;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

    //用户账号状态
    private String status;

}
