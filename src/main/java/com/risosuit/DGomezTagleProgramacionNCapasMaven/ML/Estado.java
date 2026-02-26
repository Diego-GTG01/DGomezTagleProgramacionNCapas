
package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;



public class Estado {
   @Min(value=1,message = "Selecciona una opción")
    private int IdEstado;
    private String Nombre;
    @Valid
    public Pais Pais;

    public Estado() {
        this.Pais = new Pais();
    }

    
    
    public Estado(Pais Pais) {
        this.Pais = Pais;
    }
    
    

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais Pais) {
        this.Pais = Pais;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return "Estado{" + "IdEstado=" + IdEstado + ", Nombre=" + Nombre + ", Pais=" + Pais + '}';
    }
    
    
    
}
