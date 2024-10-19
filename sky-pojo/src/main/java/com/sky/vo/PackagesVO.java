package com.sky.vo;

import com.sky.entity.PackageBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackagesVO implements Serializable {

    private Long id;

    //套餐名称
    private String packageName;

    //分类名称
    private String categoryName;

    //分类id
    private Integer categoryId;

    //套餐价格
    private BigDecimal price;

    //状态 0:停用 1:启用
    private String status;

    //描述信息
    private String description;

    //图片
    private String image;

    //更新时间
    private String updatedAt;

    //套餐和菜品的关联关系
    private List<PackageBook> packageBooks = new ArrayList<>();
}
