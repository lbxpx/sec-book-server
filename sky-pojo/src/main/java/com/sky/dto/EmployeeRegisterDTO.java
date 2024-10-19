package com.sky.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(description = "员工注册时传递的数据模型")
public class EmployeeRegisterDTO implements Serializable {
    private String username;

    private String password;

    private String email;
}
