package com.digitfellas.typchennai.network;

import java.io.Serializable;


public class APIError implements Serializable {

    private boolean error;

    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*private Error error;

    private int statusCode;
    private String name;
    private String message;
    private String ERRORCODE;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return ERRORCODE;
    }

    public void setErrorCode(String errorCode) {
        this.ERRORCODE = errorCode;
    }*/
}
