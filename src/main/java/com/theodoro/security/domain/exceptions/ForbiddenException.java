package com.theodoro.security.domain.exceptions;

import com.theodoro.security.domain.enumeration.ExceptionMessagesEnum;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {

    public ForbiddenException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ForbiddenException(final ExceptionMessagesEnum exceptionMessagesEnum) {
        super(exceptionMessagesEnum.getCode(), exceptionMessagesEnum.getMessage(), HttpStatus.FORBIDDEN);
    }
}