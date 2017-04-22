package com.jason.auth.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fuyongde on 2017/4/22.
 */
@RestController
public class IndexController {

    @GetMapping(value = "/index")
    public String index() {
        return "Hello, Spring Boot!";
    }
}
