package com.risosuit.DGomezTagleProgramacionNCapasMaven.ML;

/**
 *
 * @author ALIEN62
 */
public class ErroresArchivo {

    public int fila;
    public String dato;
    public String descripcion;

    @Override
    public String toString() {
        return "Error en fila " + fila
                + " - Campo(s): " + dato
                + " - Descripción: " + descripcion;
    }

}
