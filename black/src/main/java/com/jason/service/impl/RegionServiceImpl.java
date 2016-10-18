package com.jason.service.impl;

import com.jason.entity.Region;
import com.jason.repository.RegionDao;
import com.jason.service.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionDao regionDao;

    @Override
    public Region getById(Integer id) {
        return regionDao.findOne(id);
    }
}
