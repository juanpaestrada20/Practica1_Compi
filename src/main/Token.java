/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.IDN;

/**
 *
 * @author juanp
 */
public class Token {

    public enum Tipo {
        PALABRA_RESESERVADA,
        SLASH,
        MENOR_QUE,
        MAYOR_QUE,
        SIGNO_ADMIRACION,
        COMENTARIO,
        COMENTARIO_MULTILINEA,
        LLAVE_ABRE,
        LLAVE_CIERRA,
        PORCENTAJE,
        GUION,
        PUNTO_COMA,
        ID,
        COMILLA_DOBLE,
        CONCATENACION,
        DISYUNCION,
        SIGNO_INTERROGACION,
        ASTERISCO,
        SIGNO_MAS,
        CONJUNTO,
        VIRGULILLA,
        COMA,
        DOS_PUNTOS,
        EXPRESION_REGULAR,
        LEXEMA,
        CADENA,
        DIGITO,
        SIMBOLO
    }

    private Tipo tipoToken;
    private String valor;
    private int fila;
    private int columna;

    public Token(Tipo tipoToken, String valor, int fila, int columna) {
        this.tipoToken = tipoToken;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public String getTipoToken() {
        switch (tipoToken) {
            case PALABRA_RESESERVADA:
                return "Palabra Reservada";
            case SLASH:
                return "Diagonal";
            case MENOR_QUE:
                return "Signo Menor Que";
            case MAYOR_QUE:
                return "Signo Mayor Que";
            case SIGNO_ADMIRACION:
                return "Signo de Admiracion";
            case COMENTARIO:
                return "Comentario";
            case COMENTARIO_MULTILINEA:
                return "Comentario Multilinea";
            case LLAVE_ABRE:
                return "Llave Abre";
            case LLAVE_CIERRA:
                return "Llave Cierra";
            case PORCENTAJE:
                return "Porcentaje";
            case GUION:
                return "Guion";
            case PUNTO_COMA:
                return "Punto y Coma";
            case ID:
                return "Id";
            case COMILLA_DOBLE:
                return "Comlla Doble";
            case CONCATENACION:
                return "Concatenacion";
            case DISYUNCION:
                return "Disyuncion";
            case SIGNO_INTERROGACION:
                return "Signo de Interrogacion";
            case ASTERISCO:
                return "Cerradura de Kleene";
            case SIGNO_MAS:
                return "Cerradura Positiva";
            case CONJUNTO:
                return "Conjunto";
            case VIRGULILLA:
                return "~";
            case COMA:
                return "Coma";
            case DOS_PUNTOS:
                return "Dos Puntos";
            case EXPRESION_REGULAR:
                return "Expresion Regular";
            case LEXEMA:
                return "Lexema";
            case CADENA:
                return "Cadena";
            case DIGITO:
                return "Digito";
            case SIMBOLO:
                return "Simbolo";
            default:
                return "Caracter Desconocido";
        }
    }

    public String getValor() {
        return valor;
    }

    public void setTipoToken(Tipo tipoToken) {
        this.tipoToken = tipoToken;
    }
    
    public int getFila(){
        return fila;
    }
    
    public int getColumna(){
        return columna;
    }

}
