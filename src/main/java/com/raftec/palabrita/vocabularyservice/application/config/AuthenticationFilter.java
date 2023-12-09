package com.raftec.palabrita.vocabularyservice.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationProperties authenticationProperties;

    public AuthenticationFilter(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = Jwts.parser()
                    .requireIssuer(authenticationProperties.issuer())
                    .requireAudience(authenticationProperties.audience())
                    .setSigningKey(authenticationProperties.secret())
                    .build()
                    .parseSignedClaims(authHeader.split(" ")[1])
                    .getPayload();

            String userName = claims.get("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier",
                    String.class);

            if (userName != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = claims.get("aud", LinkedHashSet.class).stream()
                        .map(Object::toString).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userName, null, authorities.stream().map(SimpleGrantedAuthority::new).toList());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            logger.error(String.format("Error while parsing JWT: %s", e.getLocalizedMessage()));
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
