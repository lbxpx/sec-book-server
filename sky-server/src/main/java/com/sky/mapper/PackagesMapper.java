package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.PackagesPageQueryDTO;
import com.sky.entity.Package;
import com.sky.vo.PackagesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PackagesMapper {
    Page<PackagesVO> pageQuery(PackagesPageQueryDTO packagesPageQueryDTO);

    @Select("select * from packages where id = #{id}")
    Package getPackageById(Long id);


    void deleteByIds(List<Long> ids);


    void update(Package aPackage);

    void add(Package aPackage);
}
