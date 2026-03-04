package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

public interface IColoniaJPA {
    public Result getColoniaByMunicipio(int IdMunicipio);
}
