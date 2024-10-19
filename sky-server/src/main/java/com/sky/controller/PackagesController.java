package com.sky.controller;

import com.sky.dto.PackageDTO;
import com.sky.dto.PackagesPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.PackagesService;
import com.sky.vo.PackagesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@Slf4j
public class PackagesController {

    @Autowired
    private PackagesService packagesService;

    /**
     * 套餐分类分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> packagesPageQuery(PackagesPageQueryDTO packagesPageQueryDTO) {
        log.info("套餐分页:{}", packagesPageQueryDTO);
        PageResult pageResult = packagesService.pageQuery(packagesPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询套餐
     */
    @GetMapping("/{id}")
    public Result<PackagesVO> getPackagesById(@PathVariable Long id) {
        log.info("根据id查询套餐：{}", id);
        PackagesVO packagesVO = packagesService.getPackagesById(id);
        return Result.success(packagesVO);
    }

    /**
     * 新增套餐
     */
    @PostMapping
    public Result add(@RequestBody PackageDTO packageDTO) {
        log.info("新增产品：{}",packageDTO);
        packagesService.add(packageDTO);
        return Result.success();
    }

    /**
     * 批量删除套餐
     */
    @DeleteMapping
    public Result deletePackagesByIds(@RequestParam List<Long> ids) {
        log.info("批量删除套餐，{}", ids);
        packagesService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改套餐状态
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable String status, Long id) {
        log.info("修改套餐售卖状态：{}", status);
        packagesService.updateStatus(status, id);
        return Result.success();
    }
}