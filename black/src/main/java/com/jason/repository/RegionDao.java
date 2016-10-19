package com.jason.repository;

import com.jason.entity.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegionDao extends PagingAndSortingRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

}
