package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 产品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private LocalDateTime updatedAt;
    //分类名称
    private String categoryName;
    //图书编号
    private String isbn;
    //库存
    private Integer inventory;

    private LocalDateTime createdAt;

}
