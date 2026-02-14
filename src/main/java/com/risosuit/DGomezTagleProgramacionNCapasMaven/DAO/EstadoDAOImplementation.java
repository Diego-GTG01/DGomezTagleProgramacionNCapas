/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Estado;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais;
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
public class EstadoDAOImplementation implements IEstado {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getEstadoByPais(int IdPais) {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL EstadoGetByPaisSP(?,?)}",(CallableStatementCallback<Boolean>)callablestatement -> {
                callablestatement.setInt(1, IdPais);
                callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(2);
                while (resultset.next()) {      
                    Estado estado = new Estado();
                    estado.setIdEstado(resultset.getInt("IdEstado"));
                    estado.setNombre(resultset.getString("Nombre"));
                    result.Objects.add(estado);
                    
                }result.Correct= true;
            
            return true;
            });
            
        } catch (Exception e) {
            result.Correct=false;
            result.MessageException= e.getLocalizedMessage();
            result.ex=e;
        }

        return result;
    }

}
