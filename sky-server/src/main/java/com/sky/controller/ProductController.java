package com.sky.controller;

import com.sky.dto.ProductDTO;
import com.sky.dto.ProductPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.ProductService;
import com.sky.vo.CategoryListVO;
import com.sky.vo.ProductListVO;
import com.sky.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 产品分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> productPageQuery(ProductPageQueryDTO productPageQueryDTO) {
        log.info("产品分页:{}", productPageQueryDTO);
        PageResult pageResult = productService.pageQuery(productPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除产品
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("产品批量删除：{}",ids);
        productService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改产品
     * @param productDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody ProductDTO productDTO) {
        log.info("修改产品：ProductDTO:{}", productDTO);
        productService.update(productDTO);
        return Result.success();
    }

    /**
     * 根据id查询产品详情
     */
    @GetMapping("/{id}")
    public Result<ProductVO> queryBookById(@PathVariable Long id) {
        log.info("根据id查询产品详情:{}",id);
        ProductVO productVO=productService.queryBookById(id);
        return Result.success(productVO);
    }

    /**
     * 修改产品售卖状态
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable String status,Long id) {
        log.info("修改产品售卖状态：{}",status);
        productService.updateStatus(status,id);
        return Result.success();
    }

    /**
     * 新增产品
     */
    @PostMapping
    public Result add(@RequestBody ProductDTO productDTO) {
        log.info("新增产品：{}",productDTO);
        productService.add(productDTO);
        return Result.success();
    }

    /**
     * 书籍列表查询
     * @return
     */
    @GetMapping("/list")
    public Result<List<ProductListVO>> getProductList(){
        log.info("查询书籍列表");
        List<ProductListVO> list=productService.getProductList();
        return Result.success(list);
    }
}
