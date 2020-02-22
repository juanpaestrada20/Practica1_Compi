/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Desktop;
import java.awt.FileDialog;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        errores = new LinkedList<Error>();
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
                                break;
                            } else if (Character.isDigit(c)) {
                                estado = 5;
                                i--;
                                break;
                            } else {
                                int ascii = (int) c;
                                if ((ascii > 33 && ascii < 47) || ascii == 61 || ascii == 64 || (ascii > 90 && ascii < 97)) {
                                    columna++;
                                    auxlex += c;
                                    agregarToken(Token.Tipo.SIMBOLO);
                                    break;
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
                        if (!Character.isLetter(entrada.charAt(i + 1))) {
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
                    if (Character.isDigit(c)) {
                        columna++;
                        auxlex += c;
                    } else {
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

    public LinkedList<Error> errores() {
        return errores;
    }

    public void generarHTML_Tokens() throws IOException {
        Iterator<Token> it = salidaToken.iterator();
        javax.swing.JFileChooser jF1 = new javax.swing.JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo HTML", "html");
        jF1.setFileFilter(filtro);
        String ruta = "";
        try {
            if (jF1.showSaveDialog(null) == jF1.APPROVE_OPTION) {
                ruta = jF1.getSelectedFile().getAbsolutePath() + ".html";

                FileOutputStream filr;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            FileWriter archivo = new FileWriter(ruta);

            PrintWriter escritura = new PrintWriter(archivo);

            escritura.println("<html>");
            escritura.println("<head>"
                    + "<meta charset='utf-8'>"
                    + "<title>Tokens</title>");

            escritura.println(" <body>");
            escritura.println(" <h1><center>Tokens</center></h1>");
            escritura.println("<center>"
                    + "<p>"
                    + "<br>"
                    + "</p>"
                    + "<table border= 4>"
                    + "<tr>"
                    + "<td><center><b>Fila</b></center></td>"
                    + "<td><center><b>Columna</b></center></td>"
                    + "<td><center><b>Valor</b></center></td>"
                    + "<td><center><b>Tipo</b></center></td>"
                    + "</tr>");
            while (it.hasNext()) {
                Token valor = it.next();
                escritura.println("<tr>"
                        + "<td><center>" + valor.getFila() + "</center></td>"
                        + "<td><center>" + valor.getColumna() + "</center></td>"
                        + "<td><center>" + valor.getValor() + "</center></td>"
                        + "<td><center>" + valor.getTipoToken() + "</center></td>"
                );

                escritura.println("</tr>");
                escritura.println("</center>");
            }
            escritura.println("</table>");
            escritura.println("<br><br>");

            escritura.println(" </body>");
            escritura.println("</html>");

            archivo.close();
        } catch (IOException exc) {
        }

        Desktop abrir;
        File file = new File(ruta);
        if (Desktop.isDesktopSupported()) {
            abrir = Desktop.getDesktop();
            abrir.open(file);
        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos,no se puede abrir el archivo de forma automática, diríjase al destino indicado y ejecútelo manualmente: " + ruta + ".", "Sistema incompatible", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generar_HTML_Errores() throws IOException {
        Iterator<Error> it = errores.iterator();
        javax.swing.JFileChooser jF1 = new javax.swing.JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo HTML", "html");
        jF1.setFileFilter(filtro);
        String ruta = "";
        try {
            if (jF1.showSaveDialog(null) == jF1.APPROVE_OPTION) {
                ruta = jF1.getSelectedFile().getAbsolutePath() + ".html";

                FileOutputStream filr;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            FileWriter archivo = new FileWriter(ruta);

            PrintWriter escritura = new PrintWriter(archivo);

            escritura.println("<html>");
            escritura.println("<head>"
                    + "<meta charset='utf-8'>"
                    + "<title>Tokens</title>");

            escritura.println(" <body>");
            escritura.println(" <h1><center>Errores</center></h1>");
            escritura.println("<center>"
                    + "<p>"
                    + "<br>"
                    + "</p>"
                    + "<table border= 4>"
                    + "<tr>"
                    + "<td><center><b>Fila</b></center></td>"
                    + "<td><center><b>Columna</b></center></td>"
                    + "<td><center><b>Valor</b></center></td>"
                    + "<td><center><b>Descripcion</b></center></td>"
                    + "</tr>");
            while (it.hasNext()) {
                Error valor = it.next();
                escritura.println("<tr>"
                        + "<td><center>" + valor.GetFila()+ "</center></td>"
                        + "<td><center>" + valor.GetColumna() + "</center></td>"
                        + "<td><center>" + valor.GetError()+ "</center></td>"
                        + "<td><center>" + valor.GetDescripcion()+ "</center></td>"
                );

                escritura.println("</tr>");
                escritura.println("</center>");
            }
            escritura.println("</table>");
            escritura.println("<br><br>");

            escritura.println(" </body>");
            escritura.println("</html>");

            archivo.close();
        } catch (IOException exc) {
        }

        Desktop abrir;
        File file = new File(ruta);
        if (Desktop.isDesktopSupported()) {
            abrir = Desktop.getDesktop();
            abrir.open(file);
        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos,no se puede abrir el archivo de forma automática, diríjase al destino indicado y ejecútelo manualmente: " + ruta + ".", "Sistema incompatible", JOptionPane.ERROR_MESSAGE);
        }
    }

}
