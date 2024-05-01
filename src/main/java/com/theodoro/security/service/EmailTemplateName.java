package com.theodoro.security.service;

public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    private final String name;

    public String getName() {
        return name;
    }

    EmailTemplateName(String name) {
        this.name = name;
    }
}
