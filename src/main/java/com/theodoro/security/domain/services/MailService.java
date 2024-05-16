package com.theodoro.security.domain.services;

import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.repositories.AccountRepository;
import com.theodoro.security.domain.repositories.TokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
public class MailService {

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
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getAccount());
            throw new RuntimeException("Activation token has expired. A new token has been sent");
        }
        var account = accountRepository.findById(savedToken.getAccount().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        account.setEnabled(true);
        accountRepository.save(account);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    public void sendValidationEmail(Account account) throws MessagingException {
        var newToken = tokenService.save(account);
        sendEmail(
                account.getEmail(),
                account.getName(),
                MailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
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
