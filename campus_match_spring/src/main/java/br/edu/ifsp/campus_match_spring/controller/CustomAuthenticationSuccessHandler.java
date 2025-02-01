package br.edu.ifsp.campus_match_spring.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import br.edu.ifsp.campus_match_spring.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Authentication authentication) throws IOException, ServletException {

        // Get the roles of the authenticated user
        var authorities = authentication.getAuthorities();
        String redirectUrl = "/auth/login"; // Default redirect URL

        // Check for specific roles
        for (var authority : authorities) {
            if (authority.getAuthority().equals("ROLE_" + Constants.USER_ESTUDANTE)) {
                redirectUrl = "/estudantes/home";
                break;
            } else if (authority.getAuthority().equals("ROLE_" + Constants.USER_INSTITUICAO)) {
                redirectUrl = "/instituicoes/home";
                break;
            }
        }

        // Redirect the user to the appropriate page
        response.sendRedirect(redirectUrl);
    }
}
