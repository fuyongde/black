package com.jason.black.rest;

import com.jason.black.domain.param.TestParam;
import com.jason.black.exception.ServiceException;
import com.jason.black.manager.MailManager;
import com.jason.black.utils.BeanValidators;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Validator;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String sendActivationCode(@RequestParam(name = "to") String to) {
        mailManager.sendActivationCode(to, RandomUtils.nextInt(1000, 9999));
        return "success";
    }

    @PostMapping(value = "/testValidate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TestParam testValidate(@RequestBody TestParam testParam) {
        BeanValidators.validateWithException(validator, testParam);
        return testParam;
    }

    @GetMapping(value = "testOutOfMemoryError")
    public void testOutOfMemoryError() {
        ArrayList<String> list = new ArrayList();
        while (true) {
            list.add("a");
        }
    }

    @GetMapping(value = "testStackOverflowError")
    public void testStackOverflowError() {
        test(10);
    }

    public void test(Integer integer) {
        test(10);
    }

    @GetMapping(value = "testList")
    public String testList(@RequestParam List<Integer> i) {
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
        return "success";
    }

    @GetMapping(value = "/testMiss")
    public String testMissingPathVariableException() throws MissingPathVariableException {
        throw new MissingPathVariableException(null, null);
    }
}
