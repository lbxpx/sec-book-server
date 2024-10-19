package com.sky.service.impl;


import com.sky.mapper.CategoriesMapper;
import com.sky.service.CategoriesService;
import com.sky.vo.CategoryListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesMapper categoriesMapper;


    /**
     * 书籍分类数据查询
     */
    public List<CategoryListVO> getCategoryList() {
        List<CategoryListVO> list = categoriesMapper.getCategoryList();
        return list;
    }


}
