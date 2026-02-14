/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Rol;
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
public class RolDAOImplementation implements IRol {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        try {
            jdbcTemplate.execute("{CALL RolGetAllSP(?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.registerOutParameter(1,java.sql.Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(1);
                while (resultset.next()) {    
                    Rol rol = new Rol();
                    rol.setIdRol(resultset.getInt("IdRol"));
                    rol.setNombre(resultset.getString("Nombre"));
                    
                    result.Objects.add(rol);
                }
                result.Correct=true;
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
