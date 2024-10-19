package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.UsersPageQueryDTO;
import com.sky.entity.User;
import com.sky.result.ChartResult;
import com.sky.vo.UserCountByMonthVO;
import com.sky.vo.UsersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsersMapper {

    Page<UsersVO> pageQuery(UsersPageQueryDTO usersPageQueryDTO);

    @Select("select * from users where id = #{id}")
    User getById(Long id);

    void deleteByIds(List<Long> ids);

    void update(User user);

     List<UserCountByMonthVO>countUsersByMonth();
}
