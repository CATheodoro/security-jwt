package com.theodoro.security.handlers;

import com.theodoro.security.assemblers.ExceptionAssembler;
import jakarta.mail.MessagingException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

import static com.theodoro.security.handlers.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionAssembler exceptionAssembler;

    public GlobalExceptionHandler(ExceptionAssembler exceptionAssembler) {
        this.exceptionAssembler = exceptionAssembler;
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionAssembler.toEntity(ACCOUNT_LOCKED.getCode(),
                                ACCOUNT_LOCKED.getMessage(),
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionAssembler.toEntity(ACCOUNT_DISABLED.getCode(),
                                ACCOUNT_DISABLED.getMessage(),
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionAssembler.toEntity(BAD_CREDENTIALS.getCode(),
                                BAD_CREDENTIALS.getMessage(),
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exceptionAssembler.toEntity(null,
                                null,
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(e -> {
                    errors.add(e.getDefaultMessage());
                });
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exceptionAssembler.toEntity(BAD_CREDENTIALS.getCode(),
                                null,
                                null,
                                errors,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        //TODO remove exp.printStackTrace(); Add log
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exceptionAssembler.toEntity(BAD_CREDENTIALS.getCode(),
                                "Internal error, please contact the admin",
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleExceptionNotFound(NotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exceptionAssembler.toEntity(NOT_FOUND_ROLE.getCode(),
                                NOT_FOUND_ROLE.getMessage(),
                                exp.getMessage(),
                                null,
                                null,
                                Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel())
                );
    }
}
