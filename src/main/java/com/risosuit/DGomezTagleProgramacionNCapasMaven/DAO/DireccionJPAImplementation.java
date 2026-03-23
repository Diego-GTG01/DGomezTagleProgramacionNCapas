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
            Direccion direccion = entityManager.find(Direccion.class, IdDireccion);
            if (direccion != null) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion direccionML = modelMapper.map(direccion,
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
            Usuario Usuario = entityManager.find(Usuario.class, idUsuario);
            if (Usuario != null) {
                Direccion direccionJPA = modelMapper.map(Direccion,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Direccion.class);
                direccionJPA.Usuario = Usuario;
                Usuario.Direcciones.add(direccionJPA);
                entityManager.merge(Usuario);
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
    public Result Update(com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion Direccion, int IdUsuario) {
        Result Result = new Result();
        try {
            Direccion direccionExistente = entityManager.find(
                    Direccion.class,
                    Direccion.getIdDireccion());

            if (direccionExistente == null) {
                Result.Correct = false;
                Result.MessageException = "Dirección no encontrada";
                return Result;
            }

            direccionExistente.setCalle(Direccion.getCalle());
            direccionExistente.setNumeroExterior(Direccion.getNumeroExterior());
            direccionExistente.setNumeroInterior(Direccion.getNumeroInterior());

            if (Direccion.Colonia != null && Direccion.Colonia.getIdColonia() > 0) {
                Colonia colonia = entityManager.find(Colonia.class, Direccion.getColonia().getIdColonia());
                direccionExistente.setColonia(colonia);
            }

            entityManager.merge(direccionExistente);
            entityManager.flush();

            Result.Correct = true;
            Result.Object = direccionExistente;

        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
            ex.printStackTrace();
        }
        return Result;
    }

    @Transactional
    @Override
    public Result Delete(int idDireccion) {
        Result Result = new Result();
        try {
            Direccion direccion = entityManager.find(Direccion.class, idDireccion);
            if (direccion != null) {
                entityManager.remove(direccion);
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