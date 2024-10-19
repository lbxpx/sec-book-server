package com.sky.service;

import com.sky.dto.ProductDTO;
import com.sky.dto.ProductPageQueryDTO;
import com.sky.entity.Product;
import com.sky.result.PageResult;
import com.sky.vo.ProductListVO;
import com.sky.vo.ProductVO;

import java.util.List;

public interface ProductService {
    /**
     * 产品分页查询
     */
    PageResult pageQuery(ProductPageQueryDTO productPageQueryDTO);

    /**
     * 产品批量删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 修改产品信息
     * @param productDTO
     */
    void update(ProductDTO productDTO);

    /**
     * 根据产品id查询详情
     * @param id
     * @return
     */
    ProductVO queryBookById(Long id);

    /**
     * 修改产品售卖状态
     * @param status
     * @param id
     */
    void updateStatus(String status, Long id);

    /**
     * 新增产品
     * @param productDTO
     */
    void add(ProductDTO productDTO);

    /**
     * 查询书籍列表
     * @return
     */
    List<ProductListVO> getProductList();
}
