package com.sky.mapper;

import com.sky.vo.UserCountByMonthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportsMapper {
    List<UserCountByMonthVO> countAmountsByMonth();
}
