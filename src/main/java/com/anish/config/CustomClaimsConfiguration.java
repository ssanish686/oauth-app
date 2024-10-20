package com.anish.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CustomClaimsConfiguration {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return (context) -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims((claims) -> {
                   if(context.getRegisteredClient().getId().equals("api-client")) {
                       claims.put("user_name", httpServletRequest.getHeader("User-Name"));
                       claims.put("user_id", httpServletRequest.getHeader("User-Id"));
                       claims.put("user_provider", httpServletRequest.getHeader("User-Provider"));
                       claims.put("authorities", httpServletRequest.getHeader("User-Role") != null ?
                               Arrays.stream(httpServletRequest.getHeader("User-Role").split("\\s*,\\s*")).toList() :
                               Collections.emptyList());
                   }
                });
            }
        };
    }
}
