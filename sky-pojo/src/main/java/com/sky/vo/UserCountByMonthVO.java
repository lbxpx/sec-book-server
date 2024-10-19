package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCountByMonthVO {
    private String dateMonth; // 对应于 date_month
    private Long userCount;    // 对应于 user_count
}
