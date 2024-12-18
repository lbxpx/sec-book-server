package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String title;

    //分类id
    private Integer categoryId;

    //状态 0表示禁用 1表示启用
    private String status;

}
