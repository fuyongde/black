package com.jason.black.service;

import com.jason.black.domain.dto.RegionDto;
import com.jason.black.domain.entity.Region;

import java.util.List;

/**
 * The interface Region service.
 */
public interface RegionService {

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    RegionDto getById(Integer id);

    /**
     * Create region.
     *
     * @param region the region
     */
    void createRegion(Region region);

    /**
     * Gets by parent id.
     *
     * @param parentId the parent id
     * @return the by parent id
     */
    List<RegionDto> getByParentId(Integer parentId);
}
