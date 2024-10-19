package com.sky.controller;

import com.sky.result.ChartResult;
import com.sky.result.PieChartResult;
import com.sky.result.Result;
import com.sky.service.ReportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Slf4j
public class ReportsController {
    @Autowired
    private ReportsService reportsService;
    /**
     * 查询销量图表数据
     * @return
     */
    @GetMapping("/amount")
    public Result<ChartResult> count() {
        log.info("查询销量图表数据 ");
        ChartResult chartResult = reportsService.countAmountsByMonth();
        return Result.success(chartResult);
    }

    /**
     * 查询套餐销量
     */
    @GetMapping("/package")
    public Result<List<PieChartResult>> countByPackage() {
        log.info("查询套餐销量");
        List<PieChartResult> list = reportsService.coutByPackage();
        return Result.success(list);
    }
}
