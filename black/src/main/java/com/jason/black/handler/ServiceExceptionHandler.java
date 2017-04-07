package com.jason.black.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.jason.black.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Clock;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fuyongde on 2016/12/24.
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    /**
     * 处理ServiceException
     */
    @ExceptionHandler(value = {ServiceException.class})
    public final ResponseEntity<?> handleException(ServiceException ex, NativeWebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResult errorResult = new ErrorResult();
        Clock clock = Clock.systemUTC();
        errorResult.setTimestamp(clock.millis());
        errorResult.setStatus(ex.getErrorCode());
        errorResult.setError(ex.getMessage());
        errorResult.setException(ServiceException.class.getName());
        errorResult.setMessage(ex.getMessage());
        errorResult.setPath(request.getContextPath());

        if (request.getNativeRequest() instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
            errorResult.setPath(req.getServletPath());
        }

        return handleExceptionInternal(ex, errorResult, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * 处理JSR311 Validation异常.
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public final ResponseEntity<?> handleException(ConstraintViolationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> errorMessages = Maps.newHashMap();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return handleExceptionInternal(ex, errorMessages, headers, HttpStatus.BAD_REQUEST, request);
    }
}
