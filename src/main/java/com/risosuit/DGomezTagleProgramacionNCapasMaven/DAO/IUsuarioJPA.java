package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

public interface IUsuarioJPA {

    Result GetAll();
    Result GetByID(int IdUsuario);
}
