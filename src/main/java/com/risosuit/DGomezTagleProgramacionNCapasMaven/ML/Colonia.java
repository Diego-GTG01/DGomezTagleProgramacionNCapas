
package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Colonia {
    @Min(value=1,message = "Selecciona una opción")
    private int IdColonia;
    
    private String Nombre;
    private String CodigoPostal;
    @Valid
    public Municipio Municipio;

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    @Override
    public String toString() {
        return "Colonia{" + "IdColonia=" + IdColonia + ", Nombre=" + Nombre + ", CodigoPostal=" + CodigoPostal + ", Municipio=" + Municipio + '}';
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }
    
    
}
