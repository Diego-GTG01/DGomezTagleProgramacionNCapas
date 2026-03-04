package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Colonia;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getColoniaByMunicipio(int IdMunicipio) {
        Result Result = new Result();
        try {
            TypedQuery<Colonia> query = entityManager
                    .createQuery("FROM Colonia c WHERE c.Municipio.IdMunicipio = :idMunicipio ORDER BY c.Nombre ASC",
                            Colonia.class)
                    .setParameter("idMunicipio", IdMunicipio);

            List<Colonia> coloniaJPA = query.getResultList();

            ArrayList<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Colonia> coloniasML = new ArrayList<>();

            for (Colonia Colonia : coloniaJPA) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Colonia coloniaML = modelMapper.map(Colonia,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Colonia.class);
                coloniasML.add(coloniaML);
            }
            Result.Objects = new ArrayList<>(coloniasML);
            Result.Correct = true;
        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

}
