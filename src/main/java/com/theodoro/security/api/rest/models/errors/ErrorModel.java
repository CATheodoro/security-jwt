package com.theodoro.security.api.rest.models.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@JsonPropertyOrder({
        "errors"
})
public class ErrorModel extends RepresentationModel<ErrorModel> {

    @JsonProperty("errors")
    private ErrorMessageWrapperModel errors;

    public ErrorModel() {
        super.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel());
        errors = new ErrorMessageWrapperModel();
    }

    public ErrorModel(final Integer errorCode, final String errorMessage) {
        super.add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()).withSelfRel());
        errors = new ErrorMessageWrapperModel();
        errors.addErrorMessage(errorCode, errorMessage);
    }

    public void addError(final Integer errorCode, final String errorMessage) {
        if (errors == null) {
            errors = new ErrorMessageWrapperModel();
        }
        errors.addErrorMessage(errorCode, errorMessage);
    }

    public ErrorMessageWrapperModel getErrors() {
        return errors;
    }

    public void setErrors(final ErrorMessageWrapperModel errors) {
        this.errors = errors;
    }
}