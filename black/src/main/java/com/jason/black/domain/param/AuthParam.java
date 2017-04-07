package com.jason.black.domain.param;

import org.hibernate.validator.constraints.Email;

/**
 * Created by fuyongde on 2017/4/7.
 */
public class AuthParam {
    private String userId;
    @Email
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
