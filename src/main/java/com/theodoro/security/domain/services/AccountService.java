package com.theodoro.security.domain.services;

import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.repositories.AccountRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final MailService mailService;

    public AccountService(AccountRepository accountRepository, MailService mailService) {
        this.accountRepository = accountRepository;
        this.mailService = mailService;
    }

    public Account register(Account account) throws MessagingException {
        Account newAccount = accountRepository.save(account);
        mailService.sendActivationEmail(account);
        return newAccount;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
