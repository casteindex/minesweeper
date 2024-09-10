/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminas;

/**
 *
 * @author Alejandro
 */
public class TableroMostrado {

    // Atributos
    private final int filas;
    private final int columnas;
    private final char[][] matriz;
    private final char[][] matrizSecreta;
    private static final char BOMBA = 'B';
    private static final char FLAG = 'F';

    // Constructor
    public TableroMostrado(int filas, int columnas, TableroSecreto tableroSecreto) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new char[filas][columnas];
        this.matrizSecreta = tableroSecreto.getMatriz();
    }

    // Getters
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public char[][] getMatrizSecreta() {
        return matrizSecreta;
    }

    // Métodos
    public void setVacio() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = ' ';
            }
        }
    }

    public boolean descubrir(int x, int y) {
        // Si tiene una bomba
        if (matrizSecreta[x][y] == BOMBA) {
            matriz[x][y] = BOMBA;
            return false; // Usuario perdió el juego
        }

        // Mostrar el numero de la matriz secreta
        matriz[x][y] = matrizSecreta[x][y];

        // Si tiene un cero (mostrar todos los otros ceros y celdas vecinas)
        if (matrizSecreta[x][y] == '0') {
            descubrirCeros(x, y);
        }
        return true; // El juego continúa
    }

    private void descubrirCeros(int x, int y) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                // Saltar el centro de la celda
                if (di == 0 && dj == 0) {
                    continue;
                }
                /* Asegurarse de que la celda evaluada esté dentro de la matriz
                para evitar OutOfBoundExceptions */
                int filaVecino = x + di;
                int columnaVecino = y + dj;
                if (filaVecino >= 0 && filaVecino < filas
                        && columnaVecino >= 0 && columnaVecino < columnas
                        && matriz[filaVecino][columnaVecino] == ' ') {
                    matriz[filaVecino][columnaVecino] = matrizSecreta[filaVecino][columnaVecino];
                    if (matriz[filaVecino][columnaVecino] == '0') {
                        descubrirCeros(filaVecino, columnaVecino);
                    }
                }
            }
        }
    }

    public void flag(int fila, int columna) {
        /* Poner una bandera si la celda está vacía, quitarla si ya tiene una, si
        se elige una celda ya mostrada, no hacer nada */
        if (matriz[fila][columna] == ' ') {
            matriz[fila][columna] = FLAG;
        } else if (matriz[fila][columna] == FLAG) {
            matriz[fila][columna] = ' ';
        }
    }

    public void print() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }
}
