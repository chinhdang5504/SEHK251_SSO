package com.example.sso_demo.controller;

import com.example.sso_demo.dto.AuthenticationRequest;
import com.example.sso_demo.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("form") AuthenticationRequest request, HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        try {
            var var1 = authenticationService.authenticate(request);
            httpSession.setAttribute("TOKEN", var1);
            Cookie cookie = new Cookie("TOKEN", var1.getToken());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 5);
            httpServletResponse.addCookie(cookie);
            return "redirect:http://localhost:5173";
        } catch (Exception e) {
            return "redirect:http://localhost:8081/sso/login?error=" + e.getMessage();
        }
    }
}
