package com.sky.controller;

import com.sky.Utils.LoginControlUtil;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.DataRequestDTO;
import com.sky.dto.EmailDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeRegisterDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.DataRequestVO;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 员工管理
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginControlUtil loginControlUtil;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .image(employee.getImage())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        log.info("员工注册{}", employeeRegisterDTO);
        employeeService.register(employeeRegisterDTO);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/update")
    public Result updatePassword(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        log.info("员工修改密码{}", employeeRegisterDTO);
        employeeService.updatePassword(employeeRegisterDTO);
        return Result.success();
    }

    /**
     * 获取验证码
     */
    @PostMapping("/code")
    public Result getCode(@RequestBody EmailDTO emailDTO) {
        log.info("获取验证码：{}", emailDTO);
        String code = employeeService.getCode(emailDTO);
        String identity = "web2023.21"+emailDTO.getEmail();
        redisTemplate.opsForValue().set(identity, code, 2, TimeUnit.MINUTES); // 将验证码存入 Redis，设置过期时间为 2 分钟
        return Result.success();
    }

    /**
     * 校验验证码
     */
    @PostMapping("/checkcode")
    public Result check(@RequestBody EmailDTO emailDTO) {
        log.info("校验验证码{}", emailDTO);
        String code = emailDTO.getCode();
        String identity = "web2023.21"+emailDTO.getEmail();
        // 从 Redis 中获取存储的验证码
        String storedCode = (String) redisTemplate.opsForValue().get(identity);
        if (code.equals(storedCode)) {
            return Result.success();
        }else return Result.error("验证码错误！");
    }

    /**
     * 获取图片验证码
     * @return
     */
    @GetMapping("/getValidateCode")
    public Result<DataRequestVO> getValidateCode() {
        log.info("获得图片验证码{}");
        DataRequestVO dataRequestVO = new DataRequestVO();
        String identity = "web2023.21" + loginControlUtil.generateValidateCodeId();
        String code = loginControlUtil.getRandomCode();
        String imgstr = loginControlUtil.getValidateCodeImage(code);
        dataRequestVO.setImgCode(imgstr);
        dataRequestVO.setIdentity(identity);
        redisTemplate.opsForValue().set(identity, code, 2, TimeUnit.MINUTES);
        return Result.success(dataRequestVO);
    }

    /**
     * 校验图片验证码
     * @param dataRequestDTO
     * @return
     */
    @PostMapping("/testValidateInfo")
    public Result testValidateInfo(@RequestBody DataRequestDTO dataRequestDTO) {
        log.info("校验验证码：{}", dataRequestDTO);
        String code = (String) redisTemplate.opsForValue().get(dataRequestDTO.getIdentity());
        if(code == null) {
            return Result.error("验证码已过期");
        }else if(dataRequestDTO.getVerifycode().equals(code)) {
            return Result.success();
        }else return Result.error("验证码错误！");
    }

}
