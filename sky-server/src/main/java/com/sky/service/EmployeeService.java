package com.sky.service;

import com.sky.dto.DataRequestDTO;
import com.sky.dto.EmailDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeRegisterDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工注册
     * @param employeeRegisterDTO
     */
    void register(EmployeeRegisterDTO employeeRegisterDTO);

    /**
     * 修改密码
     * @param employeeRegisterDTO
     */
    void updatePassword(EmployeeRegisterDTO employeeRegisterDTO);

    /**
     * 获取验证码
     * @param emailDTO
     * @return
     */
    String getCode(EmailDTO emailDTO);

}
