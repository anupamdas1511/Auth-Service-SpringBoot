package com.auth.exceptions;

public class Exception extends RuntimeException{
    private int statusCode;
    private long epoch;
    private Object data;

    public Exception(String message, int statusCode, Object data) {
        super(message);
        this.statusCode = statusCode;
        this.epoch = System.currentTimeMillis();
        this.data = data;
    }
    public Exception(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.epoch = System.currentTimeMillis();
        this.data = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getEpoch() {
        return epoch;
    }

    public Object getData() {
        return data;
    }
}
