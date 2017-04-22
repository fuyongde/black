package com.jason.auth.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fuyongde on 2017/4/20.
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestRestController {

    private static Logger logger = LoggerFactory.getLogger(TestRestController.class);

    @GetMapping(value = "/testp")
    public String testp() {
        logger.info("受保护的资源");
        return "Success";
    }
}
