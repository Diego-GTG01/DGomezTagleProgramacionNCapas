/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ALIEN62
 */
@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result Update(Direccion Direccion) {
        Result Result = new Result();
        Result.Object = Direccion;
        try {
            jdbcTemplate.execute("{CALL DireccionUpdateSP(?,?,?,?,?)}",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setInt(1, Direccion.getIdDireccion());
                        callablestatement.setString(2, Direccion.getCalle());
                        callablestatement.setString(3, Direccion.getNumeroInterior());
                        callablestatement.setString(4, Direccion.getNumeroExterior());
                        callablestatement.setInt(5, Direccion.Colonia.getIdColonia());

                        if (callablestatement.executeUpdate() > 0) {
                            Result.Correct = true;
                        } else {
                            Result.Correct = false;
                            Result.MessageException = "Algo Salió Mal";
                        }

                        return true;
                    });

        } catch (Exception e) {
            Result.Correct = false;
            Result.MessageException = e.getLocalizedMessage();
        }
        return Result;
    }

    @Override
    public Result Delete(int idDireccion) {
        Result Result = new Result();
        try {
            jdbcTemplate.execute("{CALL DireccionDeleteSP(?)}",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setInt(1, idDireccion);
                        if (callablestatement.executeUpdate() > 0) {
                            Result.Correct = true;
                        }

                        return true;
                    });
        } catch (Exception e) {
            Result.Correct = false;
            Result.MessageException = e.getLocalizedMessage();
        }

        return Result;
    }

    @Override
    public Result GetByID(int idDireccion) {
        Result Result = new Result();
        try {
            jdbcTemplate.execute("{CALL DireccionGetById(?,?)}",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setInt(1, idDireccion);
                        callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                        callablestatement.execute();
                        ResultSet Resultset = (ResultSet) callablestatement.getObject(2);
                        if (Resultset.next()) {
                            Direccion Direccion = new Direccion();
                            Direccion.setCalle(Resultset.getString("CALLE"));
                            Direccion.setNumeroExterior(Resultset.getString("NUMEROEXTERIOR"));
                            Direccion.setNumeroInterior(Resultset.getString("NUMEROINTERIOR"));
                            Direccion.Colonia.setIdColonia(Resultset.getInt("IDCOLONIA"));
                            Direccion.Colonia.setCodigoPostal(Resultset.getString("CODIGOPOSTAL"));
                            Direccion.Colonia.setNombre(Resultset.getString("NOMBRECOLONIA"));
                            Direccion.Colonia.Municipio.setIdMunicipio(Resultset.getInt("IDMUNICIPIO"));
                            Direccion.Colonia.Municipio.setNombre(Resultset.getString("NOMBREMUNICIPIO"));
                            Direccion.Colonia.Municipio.Estado.setIdEstado(Resultset.getInt("IDESTADO"));
                            Direccion.Colonia.Municipio.Estado.setNombre(Resultset.getString("NOMBREESTADO"));
                            Direccion.Colonia.Municipio.Estado.Pais.setIdPais(Resultset.getInt("IDPAIS"));
                            Direccion.Colonia.Municipio.Estado.Pais.setNombre(Resultset.getString("NOMBREPAIS"));

                            Result.Object = Direccion;

                        }
                        Result.Correct = true;
                        Resultset.close();
                        return true;
                    });
        } catch (Exception e) {
            Result.Correct = false;
            Result.MessageException = e.getLocalizedMessage();
        }

        return Result;
    }

    @Override
    public Result Add(Direccion Direccion, int idUsuario) {
        Result Result = new Result();
        try {
            jdbcTemplate.execute("{CALL DireccionAddSP(?,?,?,?,?)}",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setString(1, Direccion.getCalle());
                        callablestatement.setString(2, Direccion.getNumeroInterior());
                        callablestatement.setString(3, Direccion.getNumeroExterior());
                        callablestatement.setInt(4, Direccion.Colonia.getIdColonia());
                        callablestatement.setInt(5, idUsuario);
                        if (callablestatement.executeUpdate() > 0) {
                            Result.Correct = true;
                        }

                        return true;
                    });

        } catch (Exception e) {
            Result.Correct = false;
            Result.MessageException = e.getLocalizedMessage();
        }

        return Result;

    }

}
