package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Rol {

    @Min(value=1,message = "Selecciona una opción")
    private int IdRol;

    private String Nombre;

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return "Rol{Nombre=" + Nombre + '}';
    }

}
