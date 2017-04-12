package com.jason.black.rest;

import com.jason.black.annotations.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fuyongde on 2017/4/8.
 */
@RestController
@RequestMapping(value = "/api/commons")
public class CommonRestController {

    /**
     * 用于产生防止重复提交的token
     * Gets token.
     */
    @GetMapping(value = "/token")
    @Token(get = true)
    public void getToken() {}

}
