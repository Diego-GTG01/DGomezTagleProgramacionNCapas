package com.risosuit.DGomezTagleProgramacionNCapasMaven.Handler;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        for (GrantedAuthority rol : roles) {

            if (rol.getAuthority().equals("ROLE_Administrador")) {
                response.sendRedirect("/Usuario");
                return;
            }

            else if (rol.getAuthority().equals("ROLE_Usuario")) {
                Usuario usuario = (Usuario) usuarioJPADAOImplementation.GetByUserName(authentication.getName()).Object;
                int idUsuario = usuario.getIdUsuario();
                request.getSession().setAttribute("idUsuario", idUsuario);
        
                response.sendRedirect("/Usuario/"+idUsuario);
                return;
            }
            else if (rol.getAuthority().equals("ROLE_Editor")) {
                response.sendRedirect("/Usuario");
                return;
                
            } else{
                response.sendRedirect("/login");
                return;
            }
        }
    }
}