package com.jason.black.manager;

/**
 * Created by fuyongde on 2017/4/6.
 */
public interface MailManager {

    void sendMail(String from, String to, String subject, String text);

    void sendActivationCode(String to);
}
