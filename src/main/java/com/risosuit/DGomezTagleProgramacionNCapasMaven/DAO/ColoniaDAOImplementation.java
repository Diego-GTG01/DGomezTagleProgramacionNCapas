/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Colonia;
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
public class ColoniaDAOImplementation implements IColonia {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getColoniaByMunicipio(int IdMunicipio) {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL ColoniaGetByMunicipioSP(?,?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.setInt(1, IdMunicipio);
                callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(2);
                while (resultset.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultset.getInt("IdColonia"));
                    colonia.setNombre(resultset.getString("Nombre"));
                    colonia.setCodigoPostal(resultset.getString("CodigoPostal"));
                    result.Objects.add(colonia);

                }
                result.Correct = true;

                return true;
            });
        } catch (Exception e) {
            result.Correct=false;
            result.MessageException=e.getLocalizedMessage();
            result.ex=e;
        }

        return result;
    }

}
