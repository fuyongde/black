package com.jason.black.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    @RequestMapping
    String index() {
        return "Hello, Spring Boot!";
    }
}
