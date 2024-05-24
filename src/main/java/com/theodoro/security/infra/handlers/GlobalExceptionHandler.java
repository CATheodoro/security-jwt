package com.theodoro.security.infra.handlers;

import com.theodoro.security.api.rest.models.errors.ErrorModel;
import com.theodoro.security.domain.exceptions.ConflictException;
import com.theodoro.security.domain.exceptions.HttpException;
import jakarta.mail.MessagingException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleException(Exception exp) {
        exp.printStackTrace();
        //TODO remove exp.printStackTrace(); Add log
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorModel(INTERNAL_SERVER_ERROR.value(), exp.getMessage()));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorModel> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorModel(ACCOUNT_LOCKED.getCode(), exp.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorModel> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorModel(ACCOUNT_DISABLED.getCode(), exp.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorModel> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorModel(BAD_CREDENTIALS.getCode(), BAD_CREDENTIALS.getMessage()));
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorModel> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorModel(INTERNAL_SERVER_ERROR.value(), exp.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        ErrorModel errorModel = new ErrorModel();
        exp.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorModel.addError(HttpStatus.BAD_REQUEST.value(), fieldError.getField() + " " + fieldError.getDefaultMessage());
        });
        return ResponseEntity
                .status(exp.getStatusCode())
                .body(errorModel);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentNotValidException(ConflictException exp) {
        ErrorModel errorModel = new ErrorModel(exp.getCode(), exp.getMessage());
        return ResponseEntity
                .status(CONFLICT)
                .location(exp.getLocation())
                .body(errorModel);
    }

    @ExceptionHandler({HttpException.class, ValidationException.class})
    public ResponseEntity<ErrorModel> httpException(final HttpException exp) {
        return ResponseEntity
                .status(exp.getHttpStatus())
                .body(new ErrorModel(exp.getCode(), exp.getMessage()));
    }
}
