package com.jason.black.rest;

import com.jason.black.annotations.Token;
import com.jason.black.domain.entity.User;
import com.jason.black.domain.param.RegisterParam;
import com.jason.black.domain.param.UserParam;
import com.jason.black.service.UserService;
import com.jason.black.utils.BeanValidators;
import java.net.URI;
import javax.validation.Validator;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by fuyongde on 2016/11/12.
 */
@RestController
@RequestMapping(value = "/api/users")
@Validated
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
    @Token(auth = true)
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

    @GetMapping(value = "/{userId}/auth")
    public ResponseEntity<?> authCode(
        @PathVariable("userId") String userId,
        @RequestParam(name = "email") @Email String email
    ) {
        userService.sendAuthMail(userId, email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{userId}/auth")
    public ResponseEntity<?> auth(
        @PathVariable("userId") String userId,
        Integer code
    ) {
        userService.auth(userId, code);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> update(@PathVariable("userId") String userId, @RequestBody UserParam userParam,
        UriComponentsBuilder uriBuilder) {
        userService.update(userId, userParam);
        URI uri = uriBuilder.path("/api/users/" + userId).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

}
