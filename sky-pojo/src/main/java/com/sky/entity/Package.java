package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 套餐
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //套餐产品关系
    private List<PackageBook> packageBooks = new ArrayList<>();

    //书籍id
    private List<Long> bookIds = new ArrayList<>();
}
