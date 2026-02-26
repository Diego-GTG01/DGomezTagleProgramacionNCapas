package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public class Municipio {

    @Min(value=1,message = "Selecciona una opción")
    private int IdMunicipio;
    private String Nombre;
    @Valid
    public Estado Estado;

    public Municipio() {
        this.Estado = new Estado();
    }
    
    

    public Municipio(Estado Estado) {
        this.Estado = Estado;
    }
    
    
    
    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }
    @Valid

    public int getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(int IdMunicipio) {
        this.IdMunicipio = IdMunicipio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return "Municipio{" + "IdMunicipio=" + IdMunicipio + ", Nombre=" + Nombre + ", Estado=" + Estado + '}';
    }

}
