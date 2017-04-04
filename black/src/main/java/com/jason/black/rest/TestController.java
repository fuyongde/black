package com.jason.black.rest;

import com.jason.black.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuyongde on 2017/3/24.
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private MailSender javaMailSender;

    @GetMapping
    public String test() {
        return "success";
    }

    @PostMapping(value = "/sendMail")
    public String sendMail(
            @RequestParam(name = "from") String from,
            @RequestParam(name = "to") String to,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "text") String text
    ) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        executorService.execute(()->javaMailSender.send(mailMessage));

        return "success";
    }

    @GetMapping(value = "/testNull")
    public String testNullPointException() {
        throw new NullPointerException();
    }

    @GetMapping(value = "/testService")
    public String testServiceException() {
        throw new ServiceException();
    }

    @GetMapping(value = "/testMedia", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String testMediaTypeNotSupportedException() {
        return "Success";
    }

    @GetMapping(value = "/testMiss")
    public String testMissingPathVariableException() throws MissingPathVariableException {
        throw new MissingPathVariableException(null, null);
    }
}
