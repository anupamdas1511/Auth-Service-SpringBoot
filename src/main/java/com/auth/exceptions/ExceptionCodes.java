package com.auth.exceptions;

public enum ExceptionCodes {
    REQUEST_FULFILLED(200),
    CREATED(201),
    ACCEPTED(202),
    BAD_INPUT(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    BAD_RESPONSE(503),
    GATEWAY_TIMEOUT(504),
    GATEWAY_ERROR(505);

    private final int code;

    ExceptionCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
