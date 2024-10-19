package com.sky.controller;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.ProductPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 订单分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> ordersPageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页:{}", ordersPageQueryDTO);
        PageResult pageResult = ordersService.pageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 订单详情查询
     */
    @GetMapping("/{id}")
    public Result<OrderVO> getOrderById(@PathVariable String id) {
        log.info("订单详情查询：{}",id);
        OrderVO orderVO = ordersService.getOrderById(id);
        return Result.success(orderVO);
    }

    /**
     * 删除订单
     */
    @DeleteMapping
    public Result deleteOrderById(@RequestParam List<String> ids) {
        log.info("删除订单：{}",ids);
        ordersService.deleteBatch(ids);
        return Result.success();
    }
}
