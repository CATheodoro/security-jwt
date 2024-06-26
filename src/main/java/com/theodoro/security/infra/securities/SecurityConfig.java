package com.theodoro.security.infra.securities;

import com.theodoro.security.api.rest.controllers.RoleController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.theodoro.security.api.rest.controllers.AccountController.ACCOUNT_REGISTER_PATH;
import static com.theodoro.security.api.rest.controllers.AuthenticationController.AUTHENTICATION_AUTHENTICATE_PATH;
import static com.theodoro.security.api.rest.controllers.AuthenticationController.AUTHENTICATION_REFRESH_TOKEN_PATH;
import static com.theodoro.security.api.rest.controllers.MailController.MAIL_ACTIVATE_ACCOUNT_PATH;
import static com.theodoro.security.api.rest.controllers.MailController.MAIL_SEND_TOKEN_EMAIL_PATH;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    private static final String[] WHITELIST = {
            AUTHENTICATION_AUTHENTICATE_PATH,
            AUTHENTICATION_REFRESH_TOKEN_PATH,

            ACCOUNT_REGISTER_PATH,

            MAIL_ACTIVATE_ACCOUNT_PATH,
            MAIL_SEND_TOKEN_EMAIL_PATH,
            RoleController.ROLE_RESOURCE_PATH
    };

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITELIST).permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
