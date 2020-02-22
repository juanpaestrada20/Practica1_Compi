/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author juanp
 */
public class Error {

    private String error;
    private String descripcion;
    private int fila;
    private int columna;

    public Error(String error, String descripcion, int fila, int columna) {
        this.error = error;
        this.descripcion = descripcion;
        this.fila = fila;
        this.columna = columna;
    }

    public String GetError() {
        return error;
    }

    public String GetDescripcion() {
        return descripcion;
    }

    public int GetFila() {
        return fila;
    }

    public int GetColumna() {
        return columna;
    }
}
