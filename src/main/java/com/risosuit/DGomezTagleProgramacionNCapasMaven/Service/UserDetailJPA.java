package com.risosuit.DGomezTagleProgramacionNCapasMaven.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;

@Service
public class UserDetailJPA implements UserDetailsService {

    private final UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    public UserDetailJPA(UsuarioJPADAOImplementation usuarioJPADAOImplementation) {
        this.usuarioJPADAOImplementation = usuarioJPADAOImplementation;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result result = usuarioJPADAOImplementation.GetByUserName(username);
        Usuario usuario = (Usuario) result.Object;
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return User.withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .disabled((usuario.getActivo() == 0) ? true : false)
                .roles(usuario.getRol().getNombre())
                .build();

    }

}
