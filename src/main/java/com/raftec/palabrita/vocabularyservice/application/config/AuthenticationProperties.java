package com.raftec.palabrita.vocabularyservice.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:authentication.properties")
public class AuthenticationProperties {
    private final String issuer;
    private final String audience;
    private final String secret;

    public AuthenticationProperties(
            @Value("${authentication.issuer}") String issuer,
            @Value("${authentication.audience}") String audience,
            @Value("${authentication.secret}") String secret) {
        this.issuer = issuer;
        this.audience = audience;
        this.secret = secret;
    }

    public String issuer() {
        return issuer;
    }

    public String audience() {
        return audience;
    }

    public byte[] secret() {
        return secret.getBytes();
    }
}
