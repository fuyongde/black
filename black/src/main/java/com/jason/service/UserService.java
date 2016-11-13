package com.jason.service;

import com.jason.entity.User;

/**
 * Created by fuyongde on 2016/11/12.
 */
public interface UserService {

    User register(String username, String password);

    User getById(String id);
}
