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
        tableroSecreto.randomize();
        System.out.println("----- Tablero Secreto -----");
        tableroSecreto.print();

        System.out.println();

        // TableroSecreto mostrado
        TableroMostrado tableroMostrado = new TableroMostrado(filas, columnas, tableroSecreto);
        tableroMostrado.setVacio();

        /* ========== NOTA ==========
        Esta parte del código se removerá luego cuando se haga el GUI, por
        el momento se está imprimiendo en pantalla para definir la lógica del juego.*/
        while (true) {
            tableroMostrado.print();

            System.out.println("1) Descubrir una celda");
            System.out.println("2) Colocar/quitar bandera");

            System.out.print("Ingrese una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Capturar newline

            String comando;
            int fil, col;
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese una celda (fila-columna): ");
                    comando = scanner.nextLine();
                    fil = comando.charAt(0) - '0';
                    col = comando.charAt(2) - '0';
                    tableroMostrado.descubrir(fil, col);
                    break;
                case 2:
                    System.out.print("Ingrese una celda (fil.col): ");
                    comando = scanner.nextLine();
                    fil = comando.charAt(0) - '0';
                    col = comando.charAt(2) - '0';
                    tableroMostrado.flag(fil, col);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
            System.out.println();
        }

    }

}
