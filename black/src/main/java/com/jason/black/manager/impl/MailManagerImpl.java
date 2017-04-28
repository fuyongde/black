package com.jason.black.manager.impl;

import com.jason.black.client.Test;
import com.jason.black.manager.MailManager;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by fuyongde on 2017/4/6.
 */
@Component
public class MailManagerImpl implements MailManager {

    private static Logger logger = LoggerFactory.getLogger(MailManagerImpl.class);

    @Autowired
    private MailSender javaMailSender;

    @Override
    @Async
    public void sendMail(String from, String to, String subject, String text) {
        Assert.notNull(from, "Sender is Null");
        Assert.notNull(to, "Receiver is Null");
        Assert.notNull(subject, "Subject is Null");
        Assert.notNull(text, "Content is Null");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            if(logger.isInfoEnabled()) {
                logger.error("发送邮件失败。接收方：{}，主题：{}，内容：{}", to, subject, text);
            }
        }
    }

    @Override
    @Async
    public void sendActivationCode(String to, Integer authCode) {
        Assert.notNull(to, "Receiver is Null");
        Assert.notNull(authCode, "AuthCode is Null");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("fuyongde@126.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("激活码");
        mailMessage.setText(String.valueOf(authCode));
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            if(logger.isInfoEnabled()) {
                logger.error("发送激活码失败。接收方：{}", to);
            }
        }
    }
}
