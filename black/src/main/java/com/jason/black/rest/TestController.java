package com.jason.black.rest;

import com.jason.black.domain.dto.TestDto;
import com.jason.black.exception.ServiceException;
import com.jason.black.manager.MailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by fuyongde on 2017/3/24.
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MailManager mailManager;

    @Autowired
    private Validator validator;

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
        mailManager.sendMail(from, to, subject, text);
        return "success";
    }

    @PostMapping(value = "/sendActivationCode")
    public String sendActivationCode(
            @RequestParam(name = "to") String to
    ) {
        mailManager.sendActivationCode(to);
        return "success";
    }

    @PostMapping(value = "/testValidate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestDto testValidate(@RequestBody TestDto testDto) {
        Set constraintViolations = validator.validate(testDto);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        return testDto;
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
        return "success";
    }

    @GetMapping(value = "/testMiss")
    public String testMissingPathVariableException() throws MissingPathVariableException {
        throw new MissingPathVariableException(null, null);
    }
}
