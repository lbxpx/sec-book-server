package com.sky.dto;

import com.sky.entity.PackageBook;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PackageDTO implements Serializable {

    private Long id;

    //分类id
    private Integer categoryId;

    //套餐名称
    private String packageName;

    //套餐价格
    private BigDecimal price;

    //状态 0:停用 1:启用
    private String status;

    //描述信息
    private String description;

    //图片
    private String image;

    //套餐产品关系
    private List<PackageBook> packageBooks = new ArrayList<>();

    //书籍id
    private List<Long> bookIds = new ArrayList<>();

}
