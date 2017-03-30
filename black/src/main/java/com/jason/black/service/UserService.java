package com.jason.black.service;

import com.jason.black.domain.entity.User;

/**
 * Created by fuyongde on 2016/11/12.
 */
public interface UserService {

    User register(String username, String password);

    User getById(String id);

    void testServiceException();
}
