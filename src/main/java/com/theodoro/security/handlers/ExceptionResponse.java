package com.theodoro.security.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.Link;

import java.util.Map;
import java.util.Set;

@JsonPropertyOrder({
        "businessErrorCode",
        "messageError",
        "error",
        "validationErrors",
        "errors"
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private Integer businessErrorCode;
    private String messageError;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
    private Link links;

    public Integer getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(Integer businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Set<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Set<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
}
