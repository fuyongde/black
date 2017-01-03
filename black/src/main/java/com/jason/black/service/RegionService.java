package com.jason.black.service;

import com.jason.black.dto.RegionDto;
import com.jason.black.entity.Region;

import java.util.List;

public interface RegionService {

    RegionDto getById(Integer id);

    void createRegion(Region region);

    List<RegionDto> getByParentId(Integer parentId);
}
