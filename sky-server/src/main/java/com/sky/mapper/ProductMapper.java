package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.ProductPageQueryDTO;
import com.sky.entity.PackageBook;
import com.sky.entity.Product;
import com.sky.vo.ProductListVO;
import com.sky.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    Page<ProductVO> pageQuery(ProductPageQueryDTO productPageQueryDTO);

    @Select("select * from books where id =#{id}")
    Product getById(Long id);

    void deleteByIds(List<Long> ids);

    void update(Product product);

    void add(Product product);

    @Select("select title,id from books")
    List<ProductListVO> getProductList();

    List<PackageBook> getMsg(List<Long> bookIds);
}
