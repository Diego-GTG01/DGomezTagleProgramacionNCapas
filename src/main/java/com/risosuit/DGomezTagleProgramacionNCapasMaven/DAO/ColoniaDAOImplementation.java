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
            result.Correct = false;
            result.MessageException = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result getColoniaByCodigoPostal(String CodigoPostal) {
        Result Result = new Result();
        Result.Objects = new ArrayList<>();

        try {
            jdbcTemplate.execute("{CALL ColoniaByCodigoPostal(?,?)}", (CallableStatementCallback<Boolean>) (callablestatement) -> {
                callablestatement.setString(1, CodigoPostal);
                callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet Resultset = (ResultSet) callablestatement.getObject(2);
                while (Resultset.next()) {
                    Colonia Colonia = new Colonia();
                    Colonia.setIdColonia(Resultset.getInt("IDCOLONIA"));
                    Colonia.setNombre(Resultset.getString("NOMBRECOLONIA"));
                    Colonia.Municipio.setIdMunicipio(Resultset.getInt("IDMUNICIPIO"));
                    Colonia.Municipio.setNombre(Resultset.getString("NOMBREMUNICIPIO"));
                    Colonia.Municipio.Estado.setIdEstado(Resultset.getInt("IDESTADO"));
                    Colonia.Municipio.Estado.setNombre(Resultset.getString("NOMBREESTADO"));
                    Colonia.Municipio.Estado.Pais.setIdPais(Resultset.getInt("IDPAIS"));
                    Colonia.Municipio.Estado.Pais.setNombre(Resultset.getString("NOMBREMUNICIPIO"));
                    Result.Objects.add(Colonia);

                }
                Result.Correct=true;
                return true;
            });
        } catch (Exception e) {
            Result.Correct = false;
            Result.MessageException = e.getLocalizedMessage();
            Result.ex = e;
        }

        return Result;

    }

}
