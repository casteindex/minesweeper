/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminas;

import java.util.Random;

/**
 *
 * @author Alejandro
 */
public class Buscaminas {

    public static int[][] tablero = new int[9][9];

    public static void main(String[] args) {

        generarMinas();
        printTablero();

    }

    private static void generarMinas() {
        int numMinas = 10;
        for (int i = 0; i < numMinas; i++) {
            colocarMina();
        }
    }

    private static void colocarMina() {
        Random random = new Random();

    }

    private static void inicializarTablero() {
        /* Nota: este método no se va a utilizar en el diseño final, porque la 
        inicialización del tablero iniciaría con colocar las bombas y después el
        número de bombas adyacentes para cada celda */
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    private static void printTablero() {
        /* Método preliminar. En el programa final se usará un GUI, no se imprimirá
        el tablero en la consola */
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print("[" + tablero[i][j] + "]");
            }
            System.out.println();
        }
    }

}
