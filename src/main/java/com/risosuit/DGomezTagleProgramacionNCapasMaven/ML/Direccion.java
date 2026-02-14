/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 *
 * @author ALIEN62
 */
public class Direccion {
    
    private int IdDireccion;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    private String Calle;
    @NotEmpty(message = "Este campo no Puede estar vacio")
    private String NumeroExterior;
    private String NumeroInterior;
    @Valid
    public Colonia Colonia;

    public Direccion() {
    }

    public Direccion(int IdDireccion, String Calle, String NumeroExterior, String NumeroInterior, Colonia Colonia) {
        this.IdDireccion = IdDireccion;
        this.Calle = Calle;
        this.NumeroExterior = NumeroExterior;
        this.NumeroInterior = NumeroInterior;
        this.Colonia = Colonia;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

    @Override
    public String toString() {
        String Cadena = "";
        Cadena += "\nCalle = " + Calle;
        Cadena += "\nNumeroExterior = " + NumeroExterior;
        Cadena += "\nNumeroInterior = " + NumeroInterior;
        Cadena += "\nColonia = " + Colonia.getNombre();
        Cadena += "\nMunicipio = " + Colonia.Municipio.getNombre();
        Cadena += "\nEstado = " + Colonia.Municipio.Estado.getNombre();
        Cadena += "\nPais = " + Colonia.Municipio.Estado.Pais.getNombre();

        return Cadena;
    }

}
