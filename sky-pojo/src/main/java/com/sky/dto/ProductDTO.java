package com.sky.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {

    private Long id;
    //名称
    private String title;
    //作者
    private String author;
    //书本编号
    private String isbn;
    //出版日期
    private String publicationDate;
    //分类id
    private Integer categoryId;
    //价格
    private BigDecimal price;
    //图片
    private String coverImage;
    //库存
    private String inventory;
}
