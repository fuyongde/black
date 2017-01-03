package com.jason.black.handler;

import com.jason.black.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by fuyongde on 2016/12/24.
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理ServiceException
     */
    @ExceptionHandler(value = { ServiceException.class })
    public final ResponseEntity<?> handleException(ServiceException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResult errorResult = new ErrorResult(ex.getErrorCode(), ex.getErrorMessage());
        return handleExceptionInternal(ex, errorResult, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
