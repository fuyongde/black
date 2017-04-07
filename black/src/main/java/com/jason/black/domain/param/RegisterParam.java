package com.jason.black.domain.param;

import org.hibernate.validator.constraints.Length;

/**
 * Created by fuyongde on 2017/1/19.
 */
public class RegisterParam {
    @Length(min = 6, max = 16)
    String username;
    @Length(min = 6, max = 16)
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}