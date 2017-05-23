package com.jason.black.repository.mybatis;

import com.jason.black.domain.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fuyongde on 2016/10/24.
 */
@Mapper
public interface RegionMapper {

    @Select("SELECT * FROM region r WHERE r.id = #{id}")
    Region findById(@Param("id") Integer id);
}
