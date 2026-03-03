package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

public interface IDireccionJPA {
    public Result GetById(int IdDireccion);

    public Result Add(Direccion Direccion, int idUsuario);
    public Result Update(Direccion Direccion, int idUsuario);
    public Result Delete(int idDireccion);
;

}
