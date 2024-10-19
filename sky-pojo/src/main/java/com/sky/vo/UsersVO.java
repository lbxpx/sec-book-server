package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersVO {
    private String Id;      // 用户 ID
    private String userName;    // 用户名
    private String email;       // 用户邮箱
    private String role;        // 用户角色
    private String createdAt;    // 创建时间
    private String status;      // 状态 ('0' 表示停用，'1' 表示启用
}