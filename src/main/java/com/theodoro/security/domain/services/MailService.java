package com.theodoro.security.domain.services;

import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.exceptions.BadRequestException;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.repositories.AccountRepository;
import com.theodoro.security.domain.repositories.TokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final AccountRepository accountRepository;

    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, TokenRepository tokenRepository, TokenService tokenService, AccountRepository accountRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.accountRepository = accountRepository;
    }

    @Value("${application.spring.mail.username}")
    private String emailUsername;

    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(()-> {
            logger.error("Token not found: {}", token);
            return new NotFoundException(TOKEN_NOT_FOUND);
        });

        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendActivationEmail(savedToken.getAccount());
            throw new BadRequestException(TOKEN_EXPIRED);
        }

        Account account = accountRepository.findById(savedToken.getAccount().getId()).orElseThrow(() -> {
            logger.error("Account not found for ID: {}", savedToken.getAccount().getId());
            return new NotFoundException(ACCOUNT_ID_NOT_FOUND);
        });

        if(account.isEnabled()) {
            logger.info("Account already activated");
            throw new BadRequestException(ACCOUNT_AlREADY_ACTIVATE);
        }

        activateAccountAndSaveToken(account, savedToken);
    }

    private void activateAccountAndSaveToken(Account account, Token token) {
        account.setEnabled(true);
        accountRepository.save(account);

        token.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(token);

        logger.info("Account {} activated successfully", account.getEmail());
    }

    public void resendActivationEmail(Account account) throws MessagingException {
        Token token = tokenRepository.findTopByAccountOrderByExpiresAtDesc(account).orElseThrow(() -> new NotFoundException(TOKEN_NOT_FOUND));

        if (token.getValidatedAt() != null) {
            logger.info("Account already activated");
            throw new BadRequestException(ACCOUNT_AlREADY_ACTIVATE);
        }

        if (LocalDateTime.now().isBefore(token.getCreatedAt().plusMinutes(1))){
            throw new BadRequestException(TOKEN_CAN_NOT_RESEND);
        }

        logger.warn("Resending Token for account: {}. Sending new token email.", token.getAccount().getEmail());
        sendActivationEmail(account);
    }

    public void sendActivationEmail(Account account) throws MessagingException {
        String newToken = tokenService.save(account);
        sendEmail(
                account.getEmail(),
                account.getName(),
                MailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );

        logger.info("New activation token sent to {}", account.getEmail());
    }

    @Async
    public void sendEmail(String to, String username, MailTemplateName emailTemplate, String confirmationUrl, String activationCode, String subject) throws MessagingException {
        String templateName;
        if(emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(emailUsername);
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        javaMailSender.send(mimeMessage);
    }
}
