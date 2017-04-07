package com.jason.black.domain.dto;

import org.hibernate.validator.constraints.Length;

/**
 * Created by fuyongde on 2017/4/7.
 */
public class TestDto {
    @Length(min = 6, max = 16)
    private String username;
    @Length(min = 6, max = 16)
    private String password;

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
