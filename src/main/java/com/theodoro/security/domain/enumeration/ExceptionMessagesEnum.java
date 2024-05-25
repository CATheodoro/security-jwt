package com.theodoro.security.domain.enumeration;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ExceptionMessagesEnum {
    NO_CODE(0, NOT_IMPLEMENTED, "No code."),

    //400
    BAD_CREDENTIALS(400, BAD_REQUEST, "E-mail or Password is incorrect."),
    TOKEN_RESEND_EXPIRED(400, BAD_REQUEST, "Resending Token. Sending new token email."),
    TOKEN_CAN_NOT_RESEND(400, BAD_REQUEST, "Wait a minute for resend token"),
    TOKEN_EXPIRED(400, BAD_REQUEST, "Activation token has expired. A new token has been sent"),
    ACCOUNT_AlREADY_ACTIVATE(400, BAD_REQUEST, "Account already activated"),
    REFRESH_TOKEN_EXPIRED(400, BAD_REQUEST, "Refresh token has expired."),

    //401
    ACCOUNT_LOCKED(401, UNAUTHORIZED, "User account is locked."),
    ACCOUNT_DISABLED(401, UNAUTHORIZED, "User account is disabled."),

    //404
    ACCOUNT_EMAIL_NOT_FOUND(404, NOT_FOUND, "User account not found for email informed."),
    ACCOUNT_ID_NOT_FOUND(404, NOT_FOUND, "User account not found for id informed."),
    TOKEN_NOT_FOUND(404, NOT_FOUND, "Token informed not found, cannot activate account."),
    ROLE_ID_NOT_FOUND(404, NOT_FOUND, "Role id informed not found."),
    ROLE_NOT_INITIALIZED_NOT_FOUND(404, NOT_FOUND, "Role was not initialized."),

    //403
    NOT_AUTHORIZED(403, FORBIDDEN,"Not authorized."),

    //409
    USER_ALREADY_EXISTS(409, CONFLICT, "E-mail already registered.");

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
