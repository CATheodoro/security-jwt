package com.theodoro.security.domain.services;

public enum MailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    private final String name;

    public String getName() {
        return name;
    }

    MailTemplateName(String name) {
        this.name = name;
    }
}
