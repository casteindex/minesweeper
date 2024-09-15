/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminas;

import java.util.Scanner;

/**
 *
 * @author Alejandro
 */
public class Buscaminas {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int filas = 9;
        int columnas = 9;
        int minas = 10;

        // Generar tablero secreto
        TableroSecreto tableroSecreto = new TableroSecreto(filas, columnas, minas);
        tableroSecreto.inicializarTablero();

        // ========== Descomentar para ver el nuevo tableroSecreto ==========
        System.out.println("----- Tablero Secreto -----");
        tableroSecreto.print();

        GUI gui = new GUI(tableroSecreto);

    }

}
