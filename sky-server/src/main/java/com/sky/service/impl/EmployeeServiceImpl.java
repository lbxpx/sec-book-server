package com.sky.service.impl;

import com.sky.Utils.GetCode;
import com.sky.Utils.LoginControlUtil;
import com.sky.Utils.MailUtil;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DataRequestDTO;
import com.sky.dto.EmailDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeRegisterDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountHasExistedException;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private MailUtil mailUtil;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传来的密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 员工注册
     * @param employeeRegisterDTO
     */
    public void register(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = Employee.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role("admin")
                .build();
        BeanUtils.copyProperties(employeeRegisterDTO, employee);
        Boolean existed = employeeMapper.getByUsername(employee.getUsername()) != null;
        if (existed) {
            throw new AccountHasExistedException(MessageConstant.ACCOUNT_HAS_EXISTED);
        }
        employee.setPassword(DigestUtils.md5DigestAsHex(employeeRegisterDTO.getPassword().getBytes()));
        employeeMapper.register(employee);
    }

    /**
     * 修改密码
     * @param employeeRegisterDTO
     */
    public void updatePassword(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRegisterDTO, employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(employeeRegisterDTO.getPassword().getBytes()));
        employeeMapper.update(employee);
    }

    /**
     * 获取验证码
     * @param emailDTO
     * @return
     */
    public String getCode(EmailDTO emailDTO) {
        String code = GetCode.getSixRandom();
        mailUtil.sendVerificationCode(emailDTO.getEmail(),code);
        return code;
    }

}
