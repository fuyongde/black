package com.jason.service;

import com.jason.dto.RegionDto;
import com.jason.entity.Region;

import java.util.List;

public interface RegionService {

    RegionDto getById(Integer id);

    void createRegion(Region region);

    List<RegionDto> getByParentId(Integer parentId);
}
