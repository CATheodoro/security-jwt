package com.theodoro.security.infra.configs;

import com.theodoro.security.domain.enumeration.ExceptionMessagesEnum;
import com.theodoro.security.domain.exceptions.NotFoundException;
import com.theodoro.security.domain.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.theodoro.security.domain.enumeration.ExceptionMessagesEnum.ACCOUNT_EMAIL_NOT_FOUND;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    public UserDetailsServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_EMAIL_NOT_FOUND));
    }
}
