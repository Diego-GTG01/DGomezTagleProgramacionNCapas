/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Rol;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Colonia;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Municipio;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Estado;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Pais;
import java.sql.CallableStatement;
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
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        JdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.execute();
            ResultSet resultset = (ResultSet) callableStatement.getObject(1);
            Usuario usuarioActual = null;
            int idUsuarioActual = 0;
            result.Objects = new ArrayList<>();

            while (resultset.next()) {
                int idUsuario = resultset.getInt("IdUsuario");
                // Nuevo usuario
                if (usuarioActual == null || idUsuarioActual != idUsuario) {
                    usuarioActual = new Usuario();
                    usuarioActual.Rol = new Rol();
                    usuarioActual.Direcciones = new ArrayList<>();

                    usuarioActual.setIdUsuario(idUsuario);
                    usuarioActual.setUserName(resultset.getString("UserName"));
                    usuarioActual.setNombre(resultset.getString("NombreUsuario"));
                    usuarioActual.setApellidoPaterno(resultset.getString("ApellidoPaterno"));
                    usuarioActual.setApellidoMaterno(resultset.getString("ApellidoMaterno"));
                    usuarioActual.setEmail(resultset.getString("Email"));
                    usuarioActual.setPassword(resultset.getString("Password"));
                    usuarioActual.setFechaNacimiento(resultset.getDate("FechaNacimiento").toLocalDate());
                    usuarioActual.setSexo(resultset.getString("Sexo"));
                    usuarioActual.setTelefono(resultset.getString("Telefono"));
                    usuarioActual.setCelular(resultset.getString("Celular"));
                    usuarioActual.setCURP(resultset.getString("CURP"));
                    usuarioActual.setUltimoAcceso(resultset.getTimestamp("UltimoAcceso").toLocalDateTime());

                    usuarioActual.Rol.setIdRol(resultset.getInt("IdRol"));
                    usuarioActual.Rol.setNombre(resultset.getString("NombreRol"));

                    result.Objects.add(usuarioActual);
                    idUsuarioActual = idUsuario;
                }

                //Dirección (si existe)
                int idDireccion = resultset.getInt("IdDireccion");
                if (idDireccion != 0) {

                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();

                    direccion.setIdDireccion(idDireccion);
                    direccion.setCalle(resultset.getString("Calle"));
                    direccion.setNumeroExterior(resultset.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultset.getString("NumeroInterior"));

                    direccion.Colonia.setIdColonia(resultset.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultset.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultset.getString("CodigoPostal"));

                    direccion.Colonia.Municipio.setIdMunicipio(resultset.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultset.getString("NombreMunicipio"));

                    direccion.Colonia.Municipio.Estado.setIdEstado(resultset.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultset.getString("NombreEstado"));

                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultset.getInt("IdPais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultset.getString("NombrePais"));

                    usuarioActual.Direcciones.add(direccion);
                }
            }

            result.Correct = true;

            return true;
        });
        return result;
    }

    @Override

    public Result GetById(int IdUsuario) {
        Result result = new Result();
        result.Objects = new ArrayList<>();
        JdbcTemplate.execute("{CALL UsuarioDireccionesGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
            callablestatement.setInt(1, IdUsuario);
            callablestatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            callablestatement.execute();
            ResultSet resultset = (ResultSet) callablestatement.getObject(2);
            Usuario usuarioActual = null;

            boolean registro = true;
            result.Objects = new ArrayList<>();
            while (resultset.next()) {
                // Nuevo usuario
                if (usuarioActual == null || registro) {
                    registro = false;
                    usuarioActual = new Usuario();
                    usuarioActual.Rol = new Rol();
                    usuarioActual.Direcciones = new ArrayList<>();

                    usuarioActual.setIdUsuario(IdUsuario);
                    usuarioActual.setUserName(resultset.getString("UserName"));
                    usuarioActual.setNombre(resultset.getString("NombreUsuario"));
                    usuarioActual.setApellidoPaterno(resultset.getString("ApellidoPaterno"));
                    usuarioActual.setApellidoMaterno(resultset.getString("ApellidoMaterno"));
                    usuarioActual.setEmail(resultset.getString("Email"));
                    usuarioActual.setPassword(resultset.getString("Password"));
                    usuarioActual.setFechaNacimiento(resultset.getDate("FechaNacimiento").toLocalDate());
                    usuarioActual.setSexo(resultset.getString("Sexo"));
                    usuarioActual.setTelefono(resultset.getString("Telefono"));
                    usuarioActual.setCelular(resultset.getString("Celular"));
                    usuarioActual.setCURP(resultset.getString("CURP"));
                    usuarioActual.setUltimoAcceso(resultset.getTimestamp("UltimoAcceso").toLocalDateTime());

                    usuarioActual.Rol.setIdRol(resultset.getInt("IdRol"));
                    usuarioActual.Rol.setNombre(resultset.getString("NombreRol"));

                    result.Object = usuarioActual;

                }

                //Dirección (si existe)
                int idDireccion = resultset.getInt("IdDireccion");
                if (idDireccion != 0) {

                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();

                    direccion.setIdDireccion(idDireccion);
                    direccion.setCalle(resultset.getString("Calle"));
                    direccion.setNumeroExterior(resultset.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultset.getString("NumeroInterior"));

                    direccion.Colonia.setIdColonia(resultset.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultset.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultset.getString("CodigoPostal"));

                    direccion.Colonia.Municipio.setIdMunicipio(resultset.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultset.getString("NombreMunicipio"));

                    direccion.Colonia.Municipio.Estado.setIdEstado(resultset.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultset.getString("NombreEstado"));

                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultset.getInt("IdPais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultset.getString("NombrePais"));

                    usuarioActual.Direcciones.add(direccion);
                }
            }
            result.Correct = true;

            return true;
        });

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        JdbcTemplate.execute("{CALL UsuarioDireccionAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callablestatement -> { 
            
            callablestatement.setString(1, usuario.getUserName());
            callablestatement.setString(2, usuario.getNombre());
            callablestatement.setString(3, usuario.getApellidoPaterno());
            callablestatement.setString(4, usuario.getApellidoMaterno());
            callablestatement.setString(5, usuario.getEmail());
            callablestatement.setString(6, usuario.getPassword());
            callablestatement.setDate(7, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            callablestatement.setString(8, usuario.getSexo());
            callablestatement.setString(9, usuario.getTelefono());
            callablestatement.setString(10, usuario.getCelular());
            callablestatement.setString(11, usuario.getCURP());
            callablestatement.setInt(12, usuario.Rol.getIdRol());
            callablestatement.setString(13, usuario.Direcciones.get(0).getCalle());
            callablestatement.setString(14, usuario.Direcciones.get(0).getNumeroInterior());
            callablestatement.setString(15, usuario.Direcciones.get(0).getNumeroExterior());
            callablestatement.setInt(16, usuario.Direcciones.get(0).Colonia.getIdColonia());
            
            int data = callablestatement.executeUpdate();
            if (data != 0) {
                result.Correct = true;
            } else {
                result.Correct = false;
                result.MessageException = "No se pudo guardar el registro";
            }
        
        
        
            return true;
        });
        

        return result;
    }

}
