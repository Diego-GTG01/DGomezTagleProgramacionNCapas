package com.risosuit.DGomezTagleProgramacionNCapasMaven.Handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
                                      HttpServletResponse response, 
                                      AuthenticationException exception) 
                                      throws IOException, ServletException {
        
        String errorMessage = "Error en la autenticación";
        
        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Usuario no encontrado";
        }
        else if (exception instanceof BadCredentialsException) {
            errorMessage = "Usuario o contraseña incorrectos";
        }
            else if (exception instanceof DisabledException) {
            errorMessage = "Usuario deshabilitado";
        }
        else if (exception instanceof LockedException) {
            errorMessage = "Usuario bloqueado";
        }
        else {
            String exceptionType = exception.getClass().getSimpleName();
            errorMessage = "Error de autenticación: " + exceptionType;
            
        }
        
        request.getSession().setAttribute("loginError", errorMessage);
        
        System.out.println("Error de autenticación: " + exception.getClass().getSimpleName() + " - " + exception.getMessage());
        
        response.sendRedirect("/login?error=true");
    }
}   