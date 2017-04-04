package com.jason.black.exception;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 * Created by fuyongde on 2016/11/12.
 */
public class ServiceException extends RuntimeException {

    private int errorCode;

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
        super(properties.getProperty(String.valueOf(100000)));
        this.errorCode = 100000;
    }

    public ServiceException(int errorCode) {
        super(properties.getProperty(String.valueOf(errorCode)));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JSON_STYLE);
    }

}
