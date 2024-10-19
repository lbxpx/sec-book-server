package com.sky.controller;


import com.sky.dto.UsersPageQueryDTO;
import com.sky.result.ChartResult;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.UsersService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 用户分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> usersPageQuery(UsersPageQueryDTO usersPageQueryDTO) {
        log.info("用户分页:{}", usersPageQueryDTO);
        PageResult pageResult = usersService.pageQuery(usersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除用户
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("账户批量删除：{}",ids);
        usersService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 启用或停用账户
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用或停用账户")
    public Result<String> updateStatus(@PathVariable String status, Long id) {
        log.info("启用或停用账户：{}",status);
        usersService.updateStatus(status,id);
        return Result.success();
    }

    /**
     * 查询用户图表数据
     * @return
     */
    @GetMapping("/count")
    public Result<ChartResult> count() {
        ChartResult chartResult = usersService.countUsersByMonth();
        return Result.success(chartResult);
    }
}
