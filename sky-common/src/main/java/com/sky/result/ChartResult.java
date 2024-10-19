package com.sky.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装图表查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartResult {
    private List<String> dates;
    private List<Long> counts;
}
