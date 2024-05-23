package com.theodoro.security.domain.repositories;

import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);

    Optional<Token> findTopByAccountOrderByExpiresAtDesc(Account account);
}
