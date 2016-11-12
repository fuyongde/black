package com.jason.service.impl;

import com.jason.entity.Region;
import com.jason.repository.jdbc.RegionJdbcDao;
import com.jason.repository.jpa.RegionDao;
import com.jason.service.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionDao regionDao;

    @Resource
    private RegionJdbcDao regionJdbcDao;

    @Override
    public Region getById(Integer id) {
        return regionDao.findOne(id);
    }

    @Override
    public void createRegion(Region region) {
        regionDao.save(region);
    }
}
