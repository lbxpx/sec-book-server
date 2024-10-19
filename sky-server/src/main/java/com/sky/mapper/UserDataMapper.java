package com.sky.mapper;

import com.sky.dto.UserDataDTO;
import com.sky.vo.UserDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDataMapper {

    @Select("select * from userData where user_id = #{id}")
    UserDataVO getUserData(Integer id);

    void update(UserDataDTO userDataDTO);

    void deleteByUserIds(List<Long> ids);
}
