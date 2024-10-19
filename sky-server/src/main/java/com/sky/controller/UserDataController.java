package com.sky.controller;

import com.sky.dto.UserDataDTO;
import com.sky.result.Result;
import com.sky.service.UserDataService;
import com.sky.vo.UserDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userData")
@Slf4j
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/{id}")
    public Result<UserDataVO> getUserData(@PathVariable Integer id) {
        log.info("获取用户信息：{}",id);
        UserDataVO userDataVO = userDataService.getUserData(id);
        return Result.success(userDataVO);
    }

    @PutMapping
    public Result updateUserData(@RequestBody UserDataDTO userDataDTO) {
        log.info("修改用户信息{}",userDataDTO);
        userDataService.update(userDataDTO);
        return Result.success();
    }
}
