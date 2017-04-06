package com.jason.black.service;

/**
 * Created by fuyongde on 2017/4/6.
 */
public interface MailService {

    void sendMail(String from, String to, String subject, String text);

    void sendActivationCode(String to);
}
