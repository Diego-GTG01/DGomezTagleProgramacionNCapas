package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Municipio {

    @Min(value=1,message = "Selecciona una opción")
    private int IdMunicipio;
    private String Nombre;
    public Estado Estado;
    @Valid
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
