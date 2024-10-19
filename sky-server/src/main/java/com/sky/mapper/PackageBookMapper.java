package com.sky.mapper;

import com.sky.entity.PackageBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PackageBookMapper {

    @Select("select * from package_books where package_id=#{id}")
    List<PackageBook> getByPackageId(Long id);

    void deleteByPackagesIds(List<Long> ids);

    List<Long> getPackageIdsByBookIds(List<Long> ids);

    void insertBatch(List<PackageBook> packageBooks);
}
