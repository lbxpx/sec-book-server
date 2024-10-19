package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PackagesPageQueryDTO implements Serializable {

    //页码
    private int page;

    //每页记录数
    private int pageSize;

    //套餐名称
    private String packageName;

    //套餐状态
    private String status;

    //分类id
    private Integer categoryId;

}
