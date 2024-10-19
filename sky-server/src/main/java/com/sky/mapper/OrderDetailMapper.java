package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.result.PieChartResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> getOrderDetailById(String id);

    void deleteBatch(List<String> ids);

    List<PieChartResult> countByPackage();
}
