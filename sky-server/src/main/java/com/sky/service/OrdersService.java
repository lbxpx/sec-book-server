package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderVO;

import java.util.List;

public interface OrdersService {
    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 订单详情查询
     * @param id
     * @return
     */
    OrderVO getOrderById(String id);

    /**
     * 删除订单
     * @param ids
     */
    void deleteBatch(List<String> ids);
}
