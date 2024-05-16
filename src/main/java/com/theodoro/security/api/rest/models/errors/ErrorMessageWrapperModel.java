package com.theodoro.security.api.rest.models.errors;

import java.util.LinkedList;
import java.util.List;

public class ErrorMessageWrapperModel {
    private List<ErrorMessageResponse> messages;

    public ErrorMessageWrapperModel() {
        messages = new LinkedList<ErrorMessageResponse>();
    }

    public List<ErrorMessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(final List<ErrorMessageResponse> messages) {
        this.messages = messages;
    }

    public void addErrorMessage(final Integer errorCode, final String errorMessage) {
        messages.add(new ErrorMessageResponse(errorCode, errorMessage));
    }
}