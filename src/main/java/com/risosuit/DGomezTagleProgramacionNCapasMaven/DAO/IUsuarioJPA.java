package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Usuario;

public interface IUsuarioJPA {

    Result GetAll();
    Result GetByID(int IdUsuario);
    Result Busqueda(Usuario usuario);
    Result Add(Usuario usuario);
    Result Update(Usuario usuario);
}
