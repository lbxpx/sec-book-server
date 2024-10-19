package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.ProductDTO;
import com.sky.dto.ProductPageQueryDTO;
import com.sky.entity.Package;
import com.sky.entity.Product;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.PackageBookMapper;
import com.sky.mapper.PackagesMapper;
import com.sky.mapper.ProductMapper;
import com.sky.result.PageResult;
import com.sky.service.ProductService;
import com.sky.vo.CategoryListVO;
import com.sky.vo.ProductListVO;
import com.sky.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PackageBookMapper packageBookMapper;
    @Autowired
    private PackagesMapper packagesMapper;

    /**
     * 产品分页查询
     * @param productPageQueryDTO
     * @return
     */
    public PageResult pageQuery(ProductPageQueryDTO productPageQueryDTO) {
        PageHelper.startPage(productPageQueryDTO.getPage(),productPageQueryDTO.getPageSize());
        Page<ProductVO> page = productMapper.pageQuery(productPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 批量删除产品
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {
        //判断当前产品能否删除
        for (Long id : ids) {
            Product product = productMapper.getById(id);
            if(product.getStatus().equals(StatusConstant.ENABLE)){
                //当前用户处于启用状态，不能删除
                throw new DeletionNotAllowedException(MessageConstant.PRODUCT_ON_SALE);
            }
        }
        List<Long> packageIds = packageBookMapper.getPackageIdsByBookIds(ids);
        if(packageIds != null && packageIds.size() > 0){
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.BOOK_BE_RELATED_BY_PACKAGE);
        }
        //根据用户id集合批量删除用户数据
        productMapper.deleteByIds(ids);
    }

    /**
     * 根据id修改产品信息
     * @param productDTO
     */
    public void update(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        productMapper.update(product);
    }

    /**
     * 根据id查询产品信息
     * @param id
     * @return
     */
    public ProductVO queryBookById(Long id) {
        Product product = productMapper.getById(id);
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product,productVO);
        return productVO;
    }

    /**
     * 修改产品售卖状态
     * @param status
     * @param id
     */
    public void updateStatus(String status, Long id) {
        Product product = Product.builder()
                .status(status)
                .id(id)
                .build();
        productMapper.update(product);
        if (status == StatusConstant.DISABLE) {
            //如果是停售操作，还需要将包含当前菜品的套餐也停售
            List<Long> ids = new ArrayList<>();
            ids.add(id);

            List<Long> packageIds = packageBookMapper.getPackageIdsByBookIds(ids);
            if(packageIds != null && packageIds.size() > 0){
                for (Long packageId : packageIds) {
                    Package aPackage = Package.builder()
                            .id(packageId)
                            .status(StatusConstant.DISABLE)
                            .build();
                    packagesMapper.update(aPackage);
                }
            }
        }
    }

    /**
     * 新增产品
     * @param productDTO
     */
    public void add(ProductDTO productDTO) {
        Product product = Product.builder()
                .createdAt((LocalDateTime.now()))
                .updatedAt((LocalDateTime.now()))
                .build();
        BeanUtils.copyProperties(productDTO,product);
        productMapper.add(product);
    }

    /**
     * 查询书籍列表
     * @return
     */
    public List<ProductListVO> getProductList() {
        List<ProductListVO> list = productMapper.getProductList();
        return list;
    }

}
