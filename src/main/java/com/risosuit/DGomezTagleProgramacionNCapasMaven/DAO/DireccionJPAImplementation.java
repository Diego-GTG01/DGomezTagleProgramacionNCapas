package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Colonia;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Usuario;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository

public class DireccionJPAImplementation implements IDireccionJPA {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetById(int IdDireccion) {
        Result Result = new Result();
        try {
            Direccion DireccionJPA = entityManager.find(Direccion.class, IdDireccion);
            if (DireccionJPA != null) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion direccionML = modelMapper.map(DireccionJPA,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion.class);
                Result.Object = direccionML;
                Result.Correct = true;
            } else {
                Result.Correct = false;
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
    public Result Add(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion Direccion, int idUsuario) {
        Result Result = new Result();
        try {
            Usuario UsuarioJPA = entityManager.find(Usuario.class, idUsuario);
            if (UsuarioJPA != null) {
                Direccion DireccionJPA = modelMapper.map(Direccion,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Direccion.class);
                DireccionJPA.Usuario = UsuarioJPA;
                UsuarioJPA.Direcciones.add(DireccionJPA);
                entityManager.merge(UsuarioJPA);
                entityManager.flush();

                Result.Correct = true;

            } else {
                Result.Correct = false;
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
    public Result Update(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion DireccionML, int IdUsuario) {
        Result Result = new Result();
        try {
            Direccion DireccionJPA = entityManager.find(
                    Direccion.class,
                    DireccionML.getIdDireccion());
            if (DireccionJPA == null) {
                Result.Correct = false;
                Result.MessageException = "Dirección no encontrada";
                return Result;
            }

            if (DireccionML.Colonia != null && DireccionML.Colonia.getIdColonia() > 0) {
                Colonia colonia = entityManager.find(Colonia.class, DireccionML.getColonia().getIdColonia());
                DireccionJPA.setColonia(colonia);
            }
            entityManager.merge(DireccionJPA);
            entityManager.flush();
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
    public Result Delete(int idDireccion) {
        Result Result = new Result();
        try {
            Direccion DireccionJPA = entityManager.find(Direccion.class, idDireccion);
            if (DireccionJPA != null) {
                entityManager.remove(DireccionJPA);
                entityManager.flush();
                Result.Correct = true;
            } else {
                Result.Correct = false;
                Result.MessageException = "Recurso no encontrado";
            }

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }
        return Result;
    }

}
