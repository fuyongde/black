package com.jason.black.service;

import com.jason.black.domain.dto.RegionDTO;
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
    RegionDTO getById(Integer id);

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
    List<RegionDTO> getByParentId(Integer parentId);

    /**
     * Gets all leaf.
     *
     * @param parentId the parent id
     * @return the all leaf
     */
    List<Region> getAllLeaf(Integer parentId);
}
