package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO implements Serializable {

    private Long id;
    //名称
    private String title;
    //作者
    private String author;
    //分类id
    private Integer categoryId;
    //价格
    private BigDecimal price;
    //图片
    private String coverImage;
    //出版日期
    private String publicationDate;
    //0 停售 1 起售
    private String status;
    //更新时间
    private String updatedAt;
    //分类名称
    private String categoryName;
    //图书编号
    private String isbn;
    //库存
    private Integer inventory;



    //private Integer copies;
}
