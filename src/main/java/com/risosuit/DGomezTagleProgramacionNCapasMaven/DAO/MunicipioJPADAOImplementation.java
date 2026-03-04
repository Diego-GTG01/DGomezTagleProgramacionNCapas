package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA.Municipio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPA {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getMunicipioByEstado(int IdEstado) {
        Result Result = new Result();
        try {
            TypedQuery<Municipio> query = entityManager
                    .createQuery("FROM Municipio m WHERE m.Estado.IdEstado = :idEstado ORDER BY m.Nombre ASC", Municipio.class)
                    .setParameter("idEstado", IdEstado);

            List<Municipio> municipiosJPA = query.getResultList();

            ArrayList<com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Municipio> municipiosML = new ArrayList<>();

            for (Municipio Municipio : municipiosJPA) {
                com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Municipio municipioML = modelMapper.map(Municipio,
                        com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Municipio.class);
                municipiosML.add(municipioML);
            }
            Result.Objects = new ArrayList<>(municipiosML);
            Result.Correct = true;
        } catch (Exception ex) {
            Result.Correct = false;
            Result.MessageException = ex.getLocalizedMessage();
            Result.ex = ex;
        }

        return Result;
    }

}
