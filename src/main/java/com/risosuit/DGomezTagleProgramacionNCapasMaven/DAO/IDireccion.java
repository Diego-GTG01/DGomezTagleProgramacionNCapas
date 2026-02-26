/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.DAO;

import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Direccion;
import com.risosuit.DGomezTagleProgramacionNCapasMaven.ML.Result;

/**
 *
 * @author ALIEN62
 */
public interface IDireccion {
    public Result Add(Direccion Direccion, int idUsuario);
    public Result Update(Direccion Direccion);
    public Result Delete(int idDireccion);
    public Result GetByID(int idDireccion);
    
    
}
