package com.jason.black.service;

import com.jason.black.domain.entity.User;
import com.jason.black.domain.param.UserParam;

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

    /**
     * Send auth mail.
     *
     * @param userId the user id
     * @param email  the email
     */
    void sendAuthMail(String userId, String email);

    /**
     * Auth.
     *
     * @param userId the user id
     * @param code   the code
     */
    void auth(String userId, Integer code);

    void update(String userId, UserParam userParam);
}
