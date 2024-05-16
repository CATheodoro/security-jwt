package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.domain.services.MailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    public static final String MAIL_RESOURCE_PATH = "/api/v1/mail";
    public static final String MAIL_ACTIVATE_ACCOUNT_PATH = MAIL_RESOURCE_PATH + "/activate-account";

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping(MAIL_ACTIVATE_ACCOUNT_PATH)
    public void confirmEmail(@RequestParam String token) throws MessagingException {
        mailService.activateAccount(token);
    }
}
