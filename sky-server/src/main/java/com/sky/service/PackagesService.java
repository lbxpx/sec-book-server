package com.sky.service;

import com.sky.dto.PackageDTO;
import com.sky.dto.PackagesPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.PackagesVO;

import java.util.List;

public interface PackagesService {
    /**
     *套餐分页查询
     * @param packagesPageQueryDTO
     * @return
     */
    PageResult pageQuery(PackagesPageQueryDTO packagesPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    PackagesVO getPackagesById(Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 修改套餐状态
     * @param status
     * @param id
     */
    void updateStatus(String status, Long id);

    /**
     * 新增套餐
     * @param packageDTO
     */
    void add(PackageDTO packageDTO);
}
