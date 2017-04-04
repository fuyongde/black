package com.jason.black.handler;

import java.io.Serializable;

/**
 * Created by fuyongde on 2016/12/24.
 */
public class ErrorResult implements Serializable {

    /**
     * timestamp : 1491271552469
     * status : 500
     * error : Internal Server Error
     * exception : java.lang.NullPointerException
     * message : No message available
     * path : /black/api/test/testNull
     */

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

