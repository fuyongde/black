package com.jason.rest;

import com.jason.entity.Region;
import com.jason.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {
    @Autowired
    private RegionService regionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Region getRegionById(@PathVariable("id") Integer id) {
        Region result = regionService.getById(id);
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
