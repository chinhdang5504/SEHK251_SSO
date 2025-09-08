package com.example.sso_demo.config;

import com.example.sso_demo.dto.AuthenticationRequest;
import com.example.sso_demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationProvider {
    private final AuthenticationService authenticationService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        var response = authenticationService
                .authenticate(AuthenticationRequest.builder()
                        .username(username)
                        .password(password)
                        .build());

        return new UsernamePasswordAuthenticationToken(response.getToken(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

