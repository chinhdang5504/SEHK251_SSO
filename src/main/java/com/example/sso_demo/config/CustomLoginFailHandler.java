package com.example.sso_demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // Tự check thiếu trường trước khi tới provider:
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            response.sendRedirect("/sso/login?error=empty");
            return;
        }

        // Sai TK/MK hoặc các lỗi khác
        String code = "bad_credentials";
        if (exception instanceof LockedException)          code = "locked";
        else if (exception instanceof DisabledException)   code = "disabled";
        else if (exception instanceof CredentialsExpiredException) code = "cred_expired";
        else if (exception instanceof AccountExpiredException)     code = "acct_expired";
        // UsernameNotFound thường bị wrap thành BadCredentials

        response.sendRedirect("sso/login?error=" + code);
    }
}
