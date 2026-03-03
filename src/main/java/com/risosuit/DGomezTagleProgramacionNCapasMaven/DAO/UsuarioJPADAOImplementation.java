package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario u ORDER BY u.ApellidoPaterno ASC",
                    Usuario.class);
            List<Usuario> usuariosJPA = query.getResultList();
            ArrayList<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario> usuariosML = new ArrayList<>();

            for (Usuario usuario : usuariosJPA) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuarioML = modelMapper.map(usuario,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario.class);
                usuariosML.add(usuarioML);
            }
            result.Objects = new ArrayList<>(usuariosML);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.MessageException = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result Result = new Result();
        try {

            Usuario Usuario = entityManager.find(Usuario.class, IdUsuario);
            com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuarioML = modelMapper.map(Usuario,
                    com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario.class);
            Result.Object = usuarioML;
            Result.Correct = true;
        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;

        }

        return Result;
    }

    @Override
    public Result Busqueda(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuario) {
        Result Result = new Result();
        try {

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

    @Transactional
    @Override
    public Result Add(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuario) {
        Result Result = new Result();
        try {
            Usuario usuarioJPA = modelMapper.map(usuario,
                    com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario.class);

            entityManager.persist(usuarioJPA);
            Result.Correct = true;

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

    @Transactional
    @Override
    public Result Update(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuario) {
        Result Result = new Result();
        try {
            Usuario Usuario = entityManager.find(Usuario.class, usuario.getIdUsuario());

            Usuario usuarioJPA = modelMapper.map(usuario,
                    com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario.class);
            Usuario.Direcciones = usuarioJPA.Direcciones;
            usuarioJPA.setActivo(Usuario.getActivo());
            entityManager.merge(usuarioJPA);
            Result.Correct = true;

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

    @Transactional
    @Override
    public Result Delete(int IdUsuario) {
        Result Result = new Result();
        try {
            Usuario usuario = entityManager.find(Usuario.class, IdUsuario);
            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.flush();
                Result.Correct = true;
            } else {
                Result.Correct = false;
            }
        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

}
