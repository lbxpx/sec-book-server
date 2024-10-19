package com.sky.controller;

import com.sky.result.Result;
import com.sky.service.CategoriesService;
import com.sky.vo.CategoryListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;
    /**
     * 书籍分类数据查询
     */
    @GetMapping("/list")
    public Result<List<CategoryListVO>> getCategoryList(){
        log.info("查询书籍分类");
        List<CategoryListVO> list=categoriesService.getCategoryList();
        return Result.success(list);
    }

}
