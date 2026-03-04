package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Direccion;
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
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario u ORDER BY u.Nombre ASC",
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
            if (Usuario != null) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuarioML = modelMapper.map(Usuario,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario.class);
                Result.Object = usuarioML;
                Result.Correct = true;

            } else {
                Result.Correct = true;
                Result.MessageException = "Recurso no encontrado";
            }

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
            if (Usuario != null) {
                Usuario usuarioJPA = modelMapper.map(usuario,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario.class);

                usuarioJPA.setActivo(Usuario.getActivo());
                usuarioJPA.setImagenFile(Usuario.getImagenFile());

                usuarioJPA.getDirecciones().clear();

                for (Direccion dir : Usuario.Direcciones) {
                    dir.Usuario = Usuario;
                    usuarioJPA.Direcciones.add(dir);
                }
                entityManager.merge(usuarioJPA);
                Result.Correct = true;

            } else {
                Result.Correct = true;
                Result.MessageException = "Recurso no encontrado";
            }

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

    @Transactional
    @Override
    public Result UpdateActivo(int IdUsuario, int Activo) {
        Result Result = new Result();
        try {
            Usuario usuario = entityManager.find(Usuario.class, IdUsuario);
            if (usuario != null) {
                usuario.setActivo(Activo);
                entityManager.merge(usuario);
                Result.Correct = true;
            } else {
                Result.Correct = false;
            }

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
    public Result UpdateImagen(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuario) {
        Result Result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuario.getIdUsuario());
            if (usuarioJPA != null) {
                usuarioJPA.setImagenFile(usuario.getImagenFile());
                entityManager.merge(usuarioJPA);
                Result.Correct = true;
            } else {
                Result.Correct = false;
            }

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
    public Result AddAll(List<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario> Usuarios) {
        Result Result = new Result();
        try {

            for (com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuario : Usuarios) {

                Usuario usuarioJPA = modelMapper.map(usuario,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario.class);
                usuarioJPA.setActivo(1);
                entityManager.persist(usuarioJPA);

            }
            Result.Correct = true;

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

    @Override
    public Result Busqueda(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuarioBusqueda) {
        Result Result = new Result();
        try {

            if (usuarioBusqueda.getNombre() == null) {
                usuarioBusqueda.setNombre("");
            }
            if (usuarioBusqueda.getApellidoPaterno() == null) {
                usuarioBusqueda.setApellidoPaterno("");
            }
            if (usuarioBusqueda.getApellidoMaterno() == null) {
                usuarioBusqueda.setApellidoMaterno("");
            }
            if (usuarioBusqueda.getSexo() == null) {
                usuarioBusqueda.setSexo("");
            }
            String consultaJPQL = "SELECT u FROM Usuario u WHERE LOWER(u.Nombre) LIKE LOWER(CONCAT('%', NVL(:pNombre,''), '%'))\n"
                    +
                    "AND LOWER(u.ApellidoPaterno) LIKE LOWER(CONCAT('%', NVL(:pApellidoPaterno,''), '%'))\n" +
                    "AND LOWER(u.ApellidoMaterno) LIKE LOWER(CONCAT('%', NVL(:pApellidoMaterno,''), '%'))\n" +
                    "AND LOWER(u.Sexo) LIKE LOWER(CONCAT('%', :pSexo, '%'))";

            if (usuarioBusqueda.Rol.getIdRol() != 0) {
                consultaJPQL += "\nAND u.Rol.IdRol = :pIdRol\n";
            }
            TypedQuery<Usuario> query = entityManager.createQuery(consultaJPQL + "ORDER BY u.Nombre ASC",
                    Usuario.class);
            query.setParameter("pNombre", "%" + usuarioBusqueda.getNombre().toLowerCase() + "%");
            query.setParameter("pApellidoPaterno", "%" + usuarioBusqueda.getApellidoPaterno().toLowerCase() + "%");
            query.setParameter("pApellidoMaterno", "%" + usuarioBusqueda.getApellidoMaterno().toLowerCase() + "%");
            if (usuarioBusqueda.Rol.getIdRol() != 0) {

                query.setParameter("pIdRol", usuarioBusqueda.Rol.getIdRol());
            }
            query.setParameter("pSexo", "%" + usuarioBusqueda.getSexo().toLowerCase() + "%");

            List<Usuario> usuariosJPA = query.getResultList();
            ArrayList<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario> usuariosML = new ArrayList<>();

            for (Usuario usuario : usuariosJPA) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario usuarioML = modelMapper.map(usuario,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario.class);
                usuariosML.add(usuarioML);
            }
            Result.Objects = new ArrayList<>(usuariosML);

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

}
