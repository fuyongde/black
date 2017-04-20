package com.jason.black.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.jason.black.domain.dto.RegionDTO;
import com.jason.black.domain.entity.Region;
import com.jason.black.exception.ServiceException;
import com.jason.black.repository.jdbc.RegionJdbcDAO;
import com.jason.black.repository.jpa.RegionDAO;
import com.jason.black.service.RegionService;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RegionServiceImpl implements RegionService, InitializingBean {

    @Resource
    private RegionDAO regionDAO;

    @Resource
    private RegionJdbcDAO regionJdbcDAO;

    private static Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    private static Cache<Integer, Region> allRegionCache = CacheBuilder.newBuilder().build();

    private static Cache<Integer, Region> regionCache = CacheBuilder.newBuilder().maximumSize(1000).build();

    private static Cache<Integer, List<Region>> regionListCache = CacheBuilder.newBuilder().maximumSize(500).build();

    @Override
    public RegionDTO getById(Integer id) {
        try {
            Region region = regionCache.get(id, () -> regionDAO.findOne(id));
            DozerBeanMapper mapper = new DozerBeanMapper();
            RegionDTO regionDTO = mapper.map(region, RegionDTO.class);
            if (!region.getLeaf()) {
                List<Region> regions = regionListCache.get(region.getId(), () -> regionDAO.findByParentId(region.getId()));
                List<RegionDTO> regionDTOS = mapper.map(regions, List.class);
                regionDTO.setChild(regionDTOS);
            }
            return regionDTO;
        } catch (ExecutionException e) {
            throw new ServiceException(100001);
        }
    }

    @Override
    public void createRegion(Region region) {
        regionDAO.save(region);
    }

    @Override
    public List<RegionDTO> getByParentId(Integer parentId) {
        try {
            List<Region> regions = regionListCache.get(parentId, () -> regionDAO.findByParentId(parentId));
            DozerBeanMapper mapper = new DozerBeanMapper();
            List<RegionDTO> regionDTOS = mapper.map(regions, List.class);
            return regionDTOS;
        } catch (ExecutionException e) {
            throw new ServiceException(100001);
        }
    }

    @Override
    public List<Region> getAllLeaf(Integer parentId) {
        return breadth(parentId);
    }

    /**
     * Breadth list (广度优先遍历).
     *
     * @param parentId the parent id
     * @return the list
     */
    private List<Region> breadth(Integer parentId) {
        Map<Integer, Region> allRegionMap = allRegionCache.asMap();
        List<Region> regions = Lists.newArrayList();
        for (Map.Entry<Integer, Region> entry : allRegionMap.entrySet()) {
            regions.add(entry.getValue());
        }
        Deque<Region> nodeDeque = new ArrayDeque<>();
        Region node = allRegionMap.get(parentId);
        nodeDeque.add(node);
        List<Region> results = Lists.newArrayList();
        while (!nodeDeque.isEmpty()) {
            node = nodeDeque.removeFirst();
            List<Region> children = getChildren(regions, node);
            results.add(node);
            if (!CollectionUtils.isEmpty(children)) {
                children.forEach(region -> nodeDeque.offer(region));
            }
        }
        return results;
    }

    /**
     * Depth list (深度优先遍历子节点).
     *
     * @param parentId the parent id
     * @return the list
     */
    private List<Region> depth(Integer parentId) {
        Map<Integer, Region> allRegionMap = allRegionCache.asMap();
        List<Region> regions = Lists.newArrayList();
        for (Map.Entry<Integer, Region> entry : allRegionMap.entrySet()) {
            regions.add(entry.getValue());
        }
        Stack<Region> nodeStack = new Stack<>();
        Region node = allRegionMap.get(parentId);
        nodeStack.add(node);
        List<Region> results = Lists.newArrayList();
        while (!nodeStack.isEmpty()) {
            node = nodeStack.pop();
            List<Region> children = getChildren(regions, node);
            results.add(node);
            if (!CollectionUtils.isEmpty(children)) {
                children.forEach(region -> nodeStack.push(region));
            }
        }

        return results;
    }

    public List<Region> getChildren(List<Region> allData, Region parent) {
        return allData.stream()
            .filter(region -> region.getParentId().intValue() == parent.getId().intValue())
            .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Iterable<Region> allRegions = regionDAO.findAll();
        logger.info("=======================Load all Region to cache start==========================");
        allRegions.forEach(region -> allRegionCache.put(region.getId(), region));
        logger.info("=======================Load {} Region to cache end============================", allRegionCache.size());
    }
}
