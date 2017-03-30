package com.jason.black.utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by fuyongde on 2017/3/26.
 */
public class UnirestHttp {

    public static void main(String[] args) throws UnirestException {
        Unirest.post("http://www.baidu.com").asJson();
    }

}
