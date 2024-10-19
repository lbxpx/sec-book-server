package com.sky.mapper;


import com.sky.vo.CategoryListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoriesMapper {

    @Select("select name,id from categories")
    List<CategoryListVO> getCategoryList();

}
