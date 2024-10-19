package com.sky.service;


import com.sky.vo.CategoryListVO;

import java.util.List;

public interface CategoriesService {

    /**
     * 书籍分类数据查询
     */
    List<CategoryListVO> getCategoryList();

}
