/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminas;

/**
 *
 * @author Alejandro
 */
public class Minesweeper {

    public static void main(String[] args) {

        int filas = 9;
        int columnas = 9;
        int minas = 9;

        // Generar tablero secreto
        TableroSecreto tableroSecreto = new TableroSecreto(filas, columnas, minas);
        tableroSecreto.randomize();
        tableroSecreto.print();

        System.out.println();

        // TableroSecreto mostrado
        TableroMostrado tableroMostrado = new TableroMostrado(filas, columnas, tableroSecreto);
        tableroMostrado.setVacio();
        tableroMostrado.print();

    }

}
