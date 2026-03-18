package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import java.util.List;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;

public interface IUsuarioJPA {

    Result GetAll();

    Result GetById(int IdUsuario);

    Result GetByUserName(String UserName);


    Result Busqueda(Usuario usuario);

    Result Add(Usuario usuario);

    Result Update(Usuario usuario);

    Result Delete(int IdUsuario);

    Result UpdateActivo(int IdUsuario, int Activo);

    public Result UpdateImagen(Usuario usuario);

    public Result AddAll(List<Usuario> Usuarios);

}
