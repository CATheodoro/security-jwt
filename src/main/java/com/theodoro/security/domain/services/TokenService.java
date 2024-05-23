package com.theodoro.security.domain.services;

import com.theodoro.security.api.rest.assemblers.TokenAssembler;
import com.theodoro.security.domain.entities.Token;
import com.theodoro.security.domain.entities.Account;
import com.theodoro.security.domain.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenAssembler tokenAssembler;

    public TokenService(TokenRepository tokenRepository, TokenAssembler tokenAssembler) {
        this.tokenRepository = tokenRepository;
        this.tokenAssembler = tokenAssembler;
    }

    public Optional<Token> findById(Integer id) {
        return tokenRepository.findById(id);
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }

    public String save(Account account) {
        String generatedToken = generateActivationCode();
        Token token = tokenAssembler.toEntity(generatedToken, account);

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i< 6; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
