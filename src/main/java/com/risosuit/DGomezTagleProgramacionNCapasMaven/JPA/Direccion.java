
package com.risosuit.DGomezTagleProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;
    
    @Column(name = "calle")
    private String Calle;

    @Column(name = "numeroexterior")
    private String NumeroExterior;

    @Column(name = "numerointerior")
    private String NumeroInterior;

    @ManyToOne
    @JoinColumn(name = "idcolonia")
    public Colonia Colonia;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    public Usuario Usuario;

    public Direccion() {
        this.Colonia = new Colonia();

    }

    public Direccion(Colonia Colonia) {
        this.Colonia = Colonia;
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
