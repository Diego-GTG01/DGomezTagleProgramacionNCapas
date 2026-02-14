/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Estado;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Municipio;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ALIEN62
 */
@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getMunicipioByEstado(int IdPais) {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL MunicipioGetByEstadoSP(?,?)}", (CallableStatementCallback<Boolean>) (callablestatement) -> {
                callablestatement.setInt(1, IdPais);
                callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(2);
                while (resultset.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultset.getInt("IdMunicipio"));
                    municipio.setNombre(resultset.getString("Nombre"));
                    result.Objects.add(municipio);

                }
                result.Correct = true;

                return true;
            });
        } catch (Exception e) {
            result.Correct = false;
            result.MessageException = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
