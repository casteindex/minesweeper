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
public class Minesweeper {

    public static Random random = new Random();
    public static char[][] tablero = new char[9][9];

    public static void main(String[] args) {

        inicializarTablero();
        generarMinas();
        cantidadMinas();
        printTablero();

    }

    private static void cantidadMinas() {
        int filas = tablero.length;
        int columnas = tablero[0].length;
        int cuenta;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Si la celda tiene una bomba, saltarla
                if (tablero[i][j] == 'B') {
                    continue;
                }
                // Loop sobre las celdas vecinas (di, dj) in range [-1, 0, 1]
                cuenta = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        // Saltar el centro de la celda
                        if (di == 0 && dj == 0) {
                            continue;
                        }
                        /* Asegurarse de que el vecino esté dentro de la matriz
                        para evitar OutOfBoundExceptions */
                        int filaVecino = i + di;
                        int columnaVecino = j + dj;
                        if (filaVecino >= 0 && filaVecino < filas
                                && columnaVecino >= 0 && columnaVecino < columnas) {
                            if (tablero[filaVecino][columnaVecino] == 'B') {
                                cuenta++;
                            }
                        }
                    }
                }
                tablero[i][j] = (char) (cuenta + '0');
            }
        }
    }

    private static void generarMinas() {
        int numMinas = 10;
        for (int i = 0; i < numMinas; i++) {
            colocarMina();
        }
    }

    private static void colocarMina() {
        /* Verificar que la bomba colocada no tome el lugar de otra. Es decir,
        solo se puede agregar una bomba en una celda vacía */
        int fila = random.nextInt(tablero.length);
        int columna = random.nextInt(tablero[0].length);
        if (tablero[fila][columna] != 'B') {
            tablero[fila][columna] = 'B';
            return;
        }
        colocarMina();
    }

    private static void inicializarTablero() {
        /* Nota: este método no se va a utilizar en el diseño final, porque la 
        inicialización del tablero iniciaría con colocar las bombas y después el
        número de bombas adyacentes para cada celda */
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = ' ';
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
