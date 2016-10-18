package com.jason.rest;

import com.jason.entity.Region;
import com.jason.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

}
