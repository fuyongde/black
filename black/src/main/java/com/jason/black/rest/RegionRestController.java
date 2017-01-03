package com.jason.black.rest;

import com.jason.black.dto.RegionDto;
import com.jason.black.entity.Region;
import com.jason.black.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {
    @Autowired
    private RegionService regionService;

    /**
     * 根据id获取地区信息
     *
     * @param id 地区id
     * @return
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RegionDto getRegionById(@PathVariable("id") Integer id) {
        RegionDto result = regionService.getById(id);
        return result;
    }

    /**
     * 根据父级id，获取地区列表
     *
     * @param parentId 父级id
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<RegionDto> getRegionByParentId(@RequestParam(name = "parentId") Integer parentId) {
        List<RegionDto> result = regionService.getByParentId(parentId);
        return result;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@RequestBody Region region, UriComponentsBuilder uriBuilder) {
        // 保存地区
        regionService.createRegion(region);
        // 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        Integer id = region.getId();
        URI uri = uriBuilder.path("/api/regions/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

}
