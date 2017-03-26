package com.jason.black.utils;

import com.mashape.unirest.http.Unirest;

/**
 * Created by fuyongde on 2017/3/26.
 */
public class UnirestHttp {

    public static void main(String[] args) {
        Unirest.post("http://www.baidu.com");
    }

}
