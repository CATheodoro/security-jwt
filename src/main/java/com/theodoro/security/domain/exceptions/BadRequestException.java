package com.theodoro.security.domain.exceptions;

import com.theodoro.security.domain.enumeration.ExceptionMessagesEnum;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(final ExceptionMessagesEnum exceptionMessagesEnum) {
        super(exceptionMessagesEnum.getCode(), HttpStatus.BAD_REQUEST, exceptionMessagesEnum.getMessage());
    }
}
