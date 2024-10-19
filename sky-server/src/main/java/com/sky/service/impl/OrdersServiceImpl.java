package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.OrderStatusConstant;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrdersMapper;
import com.sky.result.PageResult;
import com.sky.service.OrdersService;
import com.sky.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    public PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        Page<OrderVO> page = ordersMapper.pageQuery(ordersPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 订单详情查询
     * @param id
     * @return
     */
    public OrderVO getOrderById(String id) {
        OrderVO orderVO = ordersMapper.getOrderById(id);
        List<OrderDetail> orderDetails = orderDetailMapper.getOrderDetailById(id);
        orderVO.setOrderDetails(orderDetails);
        return orderVO;
    }

    /**
     * 删除订单
     * @param ids
     */
    public void deleteBatch(List<String> ids) {
        ids.forEach(id -> {
            OrderVO orderVO = ordersMapper.getOrderById(id);
            //未完成的订单不能删除
            if ( !(OrderStatusConstant.COMPLETED.equals(orderVO.getStatus())) ) {
                throw new DeletionNotAllowedException(MessageConstant.ORDER_NOT_FINISHED);
            }
        });
        ordersMapper.deleteBatch(ids);
        orderDetailMapper.deleteBatch(ids);
    }
}
