package com.jason.black.rest;

import com.jason.black.domain.param.RegisterParam;
import com.jason.black.domain.entity.User;
import com.jason.black.service.UserService;
import com.jason.black.utils.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Validator;
import java.net.URI;

/**
 * Created by fuyongde on 2016/11/12.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUserById(@PathVariable("id") String id) {
        User result = userService.getById(id);
        return result;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(
            @RequestBody RegisterParam registerParam,
            UriComponentsBuilder uriBuilder
    ) {
        BeanValidators.validateWithException(validator, registerParam);
        User user = userService.register(registerParam.getUsername(), registerParam.getPassword());
        // 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = user.getId();
        URI uri = uriBuilder.path("/api/users/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

}
