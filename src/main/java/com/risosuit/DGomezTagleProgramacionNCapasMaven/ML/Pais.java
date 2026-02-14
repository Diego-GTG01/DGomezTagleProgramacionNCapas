
package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


public class Pais {
    @Min(value=1,message = "Selecciona una opción")
    private int IdPais;
   
    private String Nombre;
    
    public int getIdPais(){
        return IdPais;
    }
    public void setIdPais(int IdPais){
        this.IdPais=IdPais;
    }
    public String getNombre(){
        return Nombre;
    }
    public void setNombre(String Nombre){
        this.Nombre=Nombre;
    }

    @Override
    public String toString() {
        return "Pais{" + "IdPais=" + IdPais + ", Nombre=" + Nombre + '}';
    }
    
}
