package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.PackageDTO;
import com.sky.dto.PackagesPageQueryDTO;

import com.sky.entity.Package;
import com.sky.entity.PackageBook;
import com.sky.entity.Product;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.PackageBookMapper;
import com.sky.mapper.PackagesMapper;
import com.sky.mapper.ProductMapper;
import com.sky.result.PageResult;
import com.sky.service.PackagesService;
import com.sky.vo.PackagesVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PackagesServiceImpl implements PackagesService {

    @Autowired
    private PackagesMapper packagesMapper;
    @Autowired
    private PackageBookMapper packageBookMapper;
    @Autowired
    private ProductMapper productMapper;
    /**
     * 套餐分页查询
     * @param packagesPageQueryDTO
     * @return
     */
    public PageResult pageQuery(PackagesPageQueryDTO packagesPageQueryDTO) {
        PageHelper.startPage(packagesPageQueryDTO.getPage(),packagesPageQueryDTO.getPageSize());
        Page<PackagesVO> page = packagesMapper.pageQuery(packagesPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    public PackagesVO getPackagesById(Long id) {
        Package packages =packagesMapper.getPackageById(id);
        List<PackageBook> packageBooks = packageBookMapper.getByPackageId(id);
        PackagesVO packagesVO = new PackagesVO();
        BeanUtils.copyProperties(packages,packagesVO);
        packagesVO.setPackageBooks(packageBooks);
        return packagesVO;
    }

    /**
     * 根据id批量删除套餐
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Package aPackage = packagesMapper.getPackageById(id);
            //起售中的套餐不能删除
            if (StatusConstant.ENABLE.equals(aPackage.getStatus())) {
                throw new DeletionNotAllowedException(MessageConstant.PACKAGE_ON_SALE);
            }
        });

        //删除套餐表中的数据
        packagesMapper.deleteByIds(ids);
        //删除套餐产品关系表中数据
        packageBookMapper.deleteByPackagesIds(ids);
    }

    /**
     * 修改套餐售卖状态
     * @param status
     * @param id
     */
    public void updateStatus(String status, Long id) {
        Package aPackage = Package.builder()
                .status(status)
                .id(id)
                .build();
        packagesMapper.update(aPackage);
    }

    /**
     * 新增套餐
     * @param packageDTO
     */
    public void add(PackageDTO packageDTO) {
        Package aPackage = Package.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(packageDTO,aPackage);
        packagesMapper.add(aPackage);

        //获取生成的套餐id
        Long packageId = aPackage.getId();

        List<PackageBook> packageBooks = new ArrayList<>();

        List<Long> bookIds = packageDTO.getBookIds();

        packageBooks = productMapper.getMsg(bookIds);


        packageBooks.forEach(packageBook -> {
            packageBook.setPackageId(packageId);
        });

        //保存套餐与产品间的关联关系
        packageBookMapper.insertBatch(packageBooks);
    }

}
