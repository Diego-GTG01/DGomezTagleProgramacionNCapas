package com.risosuit.DGomezTagleProgramacionNCapasMaven.Handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        if (authentication != null && authentication.getName() != null) {
            System.out.println("Usuario " + authentication.getName() + " ha cerrado sesión");
        }

        request.getSession().setAttribute("logoutMessage", "Has cerrado sesión exitosamente");
        

        response.sendRedirect("/login");
    }
}