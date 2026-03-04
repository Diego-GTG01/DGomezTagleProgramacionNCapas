package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Pais;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result Result = new Result();
        try {
            TypedQuery<Pais> query = entityManager.createQuery("From Pais ORDER BY Nombre ASC", Pais.class);
            List<Pais> paisesJPA = query.getResultList();
            ArrayList<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais> paisesML = new ArrayList<>();

            for (Pais pais : paisesJPA) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais paisML = modelMapper.map(pais,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais.class);
                paisesML.add(paisML);
            }
            Result.Objects = new ArrayList<>(paisesML);
            Result.Correct = true;
        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

}
