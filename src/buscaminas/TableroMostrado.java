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
    private static final char MINA = 'M';
    private static final char FLAG = 'F';
    private static final char CERO = '0';
    private static final char VACIO = ' ';

    // Constructor
    public TableroMostrado(int filas, int columnas, TableroSecreto tableroSecreto) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new char[filas][columnas];
        this.matrizSecreta = tableroSecreto.getMatriz();
        inicializarMatriz();
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
    public void inicializarMatriz() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = VACIO;
            }
        }
    }

    public boolean descubrir(int x, int y) {
        // Si tiene una bomba
        if (matrizSecreta[x][y] == MINA) {
            matriz[x][y] = MINA;
            return false; // Usuario perdió el juego
        }

        // Mostrar el numero de la matriz secreta
        matriz[x][y] = matrizSecreta[x][y];

        // Si tiene un cero (mostrar todos los otros ceros y celdas vecinas)
        if (matrizSecreta[x][y] == CERO) {
            descubrirCeros(x, y);
        }
        return true; // El juego continúa
    }

    private void descubrirCeros(int fila, int columna) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) { // Saltar el centro de la celda
                    continue;
                }

                int newFila = fila + di;
                int newCol = columna + dj;

                if (esValido(newFila, newCol) && matriz[newFila][newCol] == VACIO) {
                    matriz[newFila][newCol] = matrizSecreta[newFila][newCol];

                    if (matrizSecreta[newFila][newCol] == CERO) {
                        descubrirCeros(newFila, newCol);
                    }
                }
            }
        }
    }

    private boolean esValido(int fila, int columna) {
        /* Asegurarse de que la celda evaluada esté dentro de la matriz para
        evitar OutOfBoundExceptions */
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    public void flag(int x, int y) {
        /* Poner una bandera si la celda está vacía, quitarla si ya tiene una, si
        se elige una celda ya mostrada, no hacer nada */
        if (matriz[x][y] == VACIO) {
            matriz[x][y] = FLAG;
        } else if (matriz[x][y] == FLAG) {
            matriz[x][y] = VACIO;
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
