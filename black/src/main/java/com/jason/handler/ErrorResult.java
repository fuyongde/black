package com.jason.handler;

import java.io.Serializable;

/**
 * Created by fuyongde on 2016/12/24.
 */
public class ErrorResult implements Serializable {
    int errorCode;
    String errorMessage;

    public ErrorResult(int errorCode, String errorMessage) {
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
}
