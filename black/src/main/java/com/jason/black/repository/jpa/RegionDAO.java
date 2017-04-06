package com.jason.black.repository.jpa;

import com.jason.black.domain.entity.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RegionDAO extends PagingAndSortingRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

    /**
     * 根据父级id，查询地区列表
     *
     * @param parentId 父级id
     * @return
     */
    List<Region> findByParentId(Integer parentId);
}
