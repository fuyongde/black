package com.jason.black.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jason.black.domain.dto.RegionDto;
import com.jason.black.domain.entity.Region;
import com.jason.black.exception.ServiceException;
import com.jason.black.repository.jdbc.RegionJdbcDao;
import com.jason.black.repository.jpa.RegionDao;
import com.jason.black.service.RegionService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionDao regionDao;

    @Resource
    private RegionJdbcDao regionJdbcDao;

    private static Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    private Cache<Integer, Region> regionCache = CacheBuilder.newBuilder().maximumSize(1000).build();

    private Cache<Integer, List<Region>> regionListCache = CacheBuilder.newBuilder().maximumSize(500).build();

    @Override
    public RegionDto getById(Integer id) {
        try {
            Region region = regionCache.get(id, new Callable<Region>() {
                @Override
                public Region call() throws Exception {
                    logger.info("load from db");
                    return regionDao.findOne(id);
                }
            });

            DozerBeanMapper mapper = new DozerBeanMapper();
            RegionDto regionDto = mapper.map(region, RegionDto.class);
            if (!region.getLeaf()) {
                List<Region> regions = regionListCache.get(region.getId(), new Callable<List<Region>>() {
                    @Override
                    public List<Region> call() throws Exception {
                        return regionDao.findByParentId(region.getId());
                    }
                });
                List<RegionDto> regionDtos = mapper.map(regions, List.class);
                regionDto.setChild(regionDtos);
            }
            return regionDto;
        } catch (ExecutionException e) {
            throw new ServiceException(100001);
        }
    }

    @Override
    public void createRegion(Region region) {
        regionDao.save(region);
    }

    @Override
    public List<RegionDto> getByParentId(Integer parentId) {
        try {
            List<Region> regions = regionListCache.get(parentId, new Callable<List<Region>>() {
                @Override
                public List<Region> call() throws Exception {
                    return regionDao.findByParentId(parentId);
                }
            });

            DozerBeanMapper mapper = new DozerBeanMapper();
            List<RegionDto> regionDtos = mapper.map(regions, List.class);
            return regionDtos;
        } catch (ExecutionException e) {
            throw new ServiceException(100001);
        }
    }
}
