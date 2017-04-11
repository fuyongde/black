package com.jason.black.manager;

/**
 * Created by fuyongde on 2017/4/6.
 */
public interface MailManager {

    /**
     * Send mail.
     *
     * @param from    the from
     * @param to      the to
     * @param subject the subject
     * @param text    the text
     */
    void sendMail(String from, String to, String subject, String text);

    /**
     * Send activation code.
     *
     * @param to       the to
     * @param authCode the auth code
     */
    void sendActivationCode(String to, Integer authCode);
}
