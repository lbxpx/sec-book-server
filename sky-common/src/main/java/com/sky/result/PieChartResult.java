package com.sky.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装饼图查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieChartResult {
    private Long value;
    private String name;
}
