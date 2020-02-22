/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.LinkedList;

/**
 *
 * @author juanp
 */
public class AnalizadorLexico {

    private LinkedList<Token> salidaToken;
    private int estado;
    private int fila;
    private int columna;
    private String auxlex;
    private LinkedList<Error> errores;

    public LinkedList<Token> Escanner(String entrada) {
        salidaToken = new LinkedList<Token>();
        estado = 0;
        fila = 1;
        columna = 0;
        auxlex = "";
        Character c;

        for (int i = 0; i < entrada.length(); i++) {
            c = entrada.charAt(i);
            OUTER:
            switch (estado) {
                case 0:
                    //Si es comentario
                    switch (c) {
                        case '/':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.SLASH);
                            estado = 1;
                            //Si  es comentario multilinea
                            break;
                        case '<':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.MENOR_QUE);
                            estado = 2;
                            break;
                        case '{':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.LLAVE_ABRE);
                            break;
                        case '}':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.LLAVE_CIERRA);
                            break;
                        case '\n':
                            fila++;
                            columna = 0;
                            estado = 0;
                            break;
                        case ' ':
                            columna++;
                            estado = 0;
                            break;
                        case '%':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.PORCENTAJE);
                            break;
                        case ':':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.DOS_PUNTOS);
                            break;
                        case '-':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.CONCATENACION);
                            break;
                        case '>':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.MAYOR_QUE);
                            break;
                        case ';':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.PUNTO_COMA);
                            break;
                        case '\"':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.COMILLA_DOBLE);
                            estado = 4;
                            break;
                        case '~':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.VIRGULILLA);
                            break;
                        case '.':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.CONCATENACION);
                            break;
                        case ',':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.COMA);
                            break;
                        case '*':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.ASTERISCO);
                            break;
                        case '|':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.DISYUNCION);
                            break;
                        case '+':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.SIGNO_MAS);
                            break;
                        case '?':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.SIGNO_INTERROGACION);
                            break;
                        default:
                            if (Character.isLetter(c)) {
                                estado = 3;
                                i--;
                            } else if (Character.isDigit(c)) {
                                estado = 5;
                                i--;
                            } else{
                                int ascii = (int)c;
                                if((ascii >33 && ascii < 47) || ascii == 61 || ascii == 64 || (ascii >90 && ascii < 97) ){
                                    columna++;
                                    auxlex+=c;
                                    agregarToken(Token.Tipo.SIMBOLO);
                                }
                            }
                            columna++;
                            auxlex += c;
                            agregarError(auxlex);
                            break;
                    }
                    break;
                case 1:
                    switch (c) {
                        case '/':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.SLASH);
                            estado = 1;
                            break;
                        case '\n':
                            columna++;
                            fila++;
                            agregarToken(Token.Tipo.COMENTARIO);
                            break;
                        default:
                            columna++;
                            auxlex += c;
                            break;
                    }
                    break;
                case 2:
                    switch (c) {
                        case '!':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.SIGNO_ADMIRACION);
                            estado = 2;
                            break;
                        case '\n':
                            fila++;
                            columna = 0;
                            auxlex += c;
                            estado = 2;
                            break;
                        case '>':
                            columna++;
                            auxlex += c;
                            agregarToken(Token.Tipo.MAYOR_QUE);
                            break;
                        default:
                            columna++;
                            auxlex += c;
                            break;
                    }
                    break;
                case 3:
                    auxlex += c;
                    columna++;
                    if (auxlex.toLowerCase().compareTo("conj") == 0) {
                        if (entrada.charAt(i + 1) == ' ') {
                            agregarToken(Token.Tipo.PALABRA_RESESERVADA);
                        } else {
                            estado = 3;
                        }
                    } else if (Character.isLetterOrDigit(c) || c == '_') {
                        estado = 3;
                    } else {
                        auxlex = auxlex.substring(0, auxlex.length() - 1);
                        i--;
                        columna--;
                        agregarToken(Token.Tipo.ID);
                    }
                    break;
                case 4:
                    if (c == '\"') {
                        if (auxlex.length() > 0) {
                            auxlex = auxlex.substring(0, auxlex.length() - 1);
                            agregarToken(Token.Tipo.CADENA);
                            estado = 4;
                        }
                        columna++;
                        auxlex += c;
                        agregarToken(Token.Tipo.COMILLA_DOBLE);
                    } else if (c != '\n' && c != ';') {
                        columna++;
                        auxlex += c;
                        estado = 4;
                    } else {

                        agregarToken(Token.Tipo.CADENA);
                    }
                    break;
                case 5:
                    if(Character.isDigit(c)){
                        columna++;
                        auxlex+=c;
                    }else{
                        i--;
                        columna--;
                        agregarToken(Token.Tipo.DIGITO);
                    }
                    break;
            }
        }

        return salidaToken;
    }

    public void agregarToken(Token.Tipo tipo) {
        salidaToken.addLast(new Token(tipo, auxlex, fila, columna));
        estado = 0;
        auxlex = "";
    }

    public void agregarError(String valor) {
        errores.addLast(new Error(valor, "Desconocido", fila, columna));
        auxlex = "";
        estado = 0;
    }
    
    public LinkedList<Error> errores(){
        return errores;
    }

}
