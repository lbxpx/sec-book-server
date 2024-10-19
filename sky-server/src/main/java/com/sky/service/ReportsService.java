package com.sky.service;

import com.sky.result.ChartResult;
import com.sky.result.PieChartResult;

import java.util.List;

public interface ReportsService {
    /**
     * 查询销量图表数据
     * @return
     */
    ChartResult countAmountsByMonth();



    /**
     * 查询套餐销量
     * @return
     */
    List<PieChartResult> coutByPackage();
}
