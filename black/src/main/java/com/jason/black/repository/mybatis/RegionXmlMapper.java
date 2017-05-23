package com.jason.black.repository.mybatis;

import com.jason.black.domain.entity.Region;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegionXmlMapper {
    /**
     * 根据id查询地区信息
     *
     * @param id 地区id
     * @return
     */
    Region findById(Integer id);
}
