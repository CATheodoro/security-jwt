package com.theodoro.security.domain.enumeration;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ExceptionMessagesEnum {
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),

    //400
    ACCOUNT_LOCKED(401, UNAUTHORIZED, "User account is locked"),
    ACCOUNT_DISABLED(401, UNAUTHORIZED, "User account is disabled"),
    BAD_CREDENTIALS(403, BAD_REQUEST, "E-mail or Password is incorrect"),
    NOT_AUTHORIZED(403, FORBIDDEN,"Not authorized"),
    USER_ALREADY_EXISTS(409, CONFLICT, "E-mail already registered");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    ExceptionMessagesEnum(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
