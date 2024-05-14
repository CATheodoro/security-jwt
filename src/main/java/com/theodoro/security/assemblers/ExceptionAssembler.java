package com.theodoro.security.assemblers;

import com.theodoro.security.handlers.ExceptionResponse;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ExceptionAssembler {

    public ExceptionResponse toEntity(Integer businessErrorCode,
                                           String messageError,
                                           String error,
                                           Set<String> validationErrors,
                                           Map<String, String> errors,
                                           Link link) {
        ExceptionResponse response = new ExceptionResponse();
        response.setBusinessErrorCode(businessErrorCode);
        response.setMessageError(messageError);
        response.setError(error);
        response.setValidationErrors(validationErrors);
        response.setErrors(errors);
        response.setLinks(link);
        return response;
    }
}
