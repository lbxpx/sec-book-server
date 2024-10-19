package com.sky.service.impl;

import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.ReportsMapper;
import com.sky.result.ChartResult;
import com.sky.result.PieChartResult;
import com.sky.service.ReportsService;
import com.sky.vo.UserCountByMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportsServiceImpl implements ReportsService {



    @Autowired
    private ReportsMapper reportsMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 查询销量图表数据
     * @return
     */
    public ChartResult countAmountsByMonth() {
        List<UserCountByMonthVO> list= reportsMapper.countAmountsByMonth();
        // 创建两个列表来存储 dateMonth 和 userCount
        List<String> dateMonths = new ArrayList<>();
        List<Long> amountsCountsList = new ArrayList<>();

        //数据类型与用户表一致，复用
        for (UserCountByMonthVO Count : list) {
            dateMonths.add(Count.getDateMonth()); // 添加 dateMonth 到第一个列表
            amountsCountsList.add(Count.getUserCount()); // 添加 userCount 到第二个列表
        }
        return new ChartResult(dateMonths,amountsCountsList);
    }

    /**
     * 查询套餐销量
     * @return
     */
    public List<PieChartResult> coutByPackage() {
        List<PieChartResult> list = orderDetailMapper.countByPackage();
        return list;
    }

}
