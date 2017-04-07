package com.jason.black.service;

import com.jason.black.domain.entity.User;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Register user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    User register(String username, String password);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    User getById(String id);

}
