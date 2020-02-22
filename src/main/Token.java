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
        SLASH,
        MENOR_QUE,
        MAYOR_QUE,
        SIGNO_ADMIRACION,
        COMENTARIO,
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
        CADENA
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
            case SLASH:
                return "/";
            case MENOR_QUE:
                return "<";
            case MAYOR_QUE:
                return ">";
            case SIGNO_ADMIRACION:
                return "!";
            case COMENTARIO:
                return "Comentario";
            case LLAVE_ABRE:
                return "Llave Abre";
            case LLAVE_CIERRA:
                return "Llave Cierra";
            case PORCENTAJE:
                return "%";
            case GUION:
                return "-";
            case PUNTO_COMA:
                return ";";
            case ID:
                return "Id";
            case COMILLA_DOBLE:
                return "\"";
            case CONCATENACION:
                return "Concatenacion";
            case DISYUNCION:
                return "Disyuncion";
            case SIGNO_INTERROGACION:
                return "?";
            case ASTERISCO:
                return "*";
            case SIGNO_MAS:
                return "+";
            case CONJUNTO:
                return "Conjunto";
            case VIRGULILLA:
                return "~";
            case COMA:
                return ",";
            case DOS_PUNTOS:
                return ":";
            case EXPRESION_REGULAR:
                return "Expresion Regular";
            case LEXEMA:
                return "Lexema";
            case CADENA:
                return "Cadena";
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
    
    

}
