package com.jason.exception;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 * Created by fuyongde on 2016/11/12.
 */
public class ServiceException extends RuntimeException {

    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private int errorCode;
    private String errorMessage;

    private static Logger logger = LoggerFactory.getLogger(ServiceException.class);

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new InputStreamReader(ServiceException.class.getResourceAsStream("/error.properties"), Charsets.UTF_8));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public ServiceException() {
        super();
    }

    public ServiceException(int errorCode) {
        String errorMessage = properties.getProperty(String.valueOf(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JSON_STYLE);
    }

}
