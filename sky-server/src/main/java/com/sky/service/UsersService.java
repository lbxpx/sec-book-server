package com.sky.service;

import com.sky.dto.UsersPageQueryDTO;
import com.sky.result.ChartResult;
import com.sky.result.PageResult;

import java.util.List;

public interface UsersService {

    /**
     * 用户分页查询
     * @param usersPageQueryDTO
     * @return
     */
    PageResult pageQuery(UsersPageQueryDTO usersPageQueryDTO);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 启用或停用账户
     * @param status
     * @param id
     */
    void updateStatus(String status, Long id);

    /**
     * 查询用户图表数据
     * @return
     */
    ChartResult countUsersByMonth();
}
