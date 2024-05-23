package com.theodoro.security.api.rest.controllers;

import com.theodoro.security.api.rest.models.requests.EmailRequest;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.services.AccountService;
import com.theodoro.security.domain.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ACCOUNT_EMAIL_NOT_FOUND;

@RestController
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    public static final String MAIL_RESOURCE_PATH = "/api/v1/mail";
    public static final String MAIL_ACTIVATE_ACCOUNT_PATH = MAIL_RESOURCE_PATH + "/activate-account";
    public static final String MAIL_SEND_TOKEN_EMAIL_PATH = MAIL_RESOURCE_PATH + "/send-token-email";

    private final MailService mailService;
    private final AccountService accountService;

    public MailController(MailService mailService, AccountService accountService) {
        this.mailService = mailService;
        this.accountService = accountService;
    }

    @GetMapping(MAIL_ACTIVATE_ACCOUNT_PATH)
    public void activateAccount(@RequestParam String token) throws MessagingException {

        mailService.activateAccount(token);
    }

    @GetMapping(MAIL_SEND_TOKEN_EMAIL_PATH)
    public void revalidationEmail(@RequestBody @Valid EmailRequest emailRequest) throws MessagingException {
        Account account = accountService.findByEmail(emailRequest.getEmail()).orElseThrow(() -> {
            logger.info("//TODO error log");
            return new NotFoundException(ACCOUNT_EMAIL_NOT_FOUND);
        });

        mailService.resendActivationEmail(account);
    }
}
