/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminas;

import java.util.Random;

/**
 *
 * @author Alejandro
 */
public class TableroSecreto {

    private static final Random random = new Random();

    // Atributos
    private final int filas;
    private final int columnas;
    private final int minas;
    private final char[][] matriz;
    private static final char MINA = 'M';
    private static final char CERO = '0';

    // Constructor
    public TableroSecreto(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.matriz = new char[filas][columnas];
    }

    // Getters
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getMinas() {
        return minas;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    // Métodos
    public void randomize() {
        // Generar minas
        for (int i = 0; i < minas; i++) {
            colocarMina();
        }
        generarNumeros();
    }

    private void colocarMina() {
        int x = random.nextInt(filas);
        int y = random.nextInt(columnas);
        if (matriz[x][y] != MINA) {
            matriz[x][y] = MINA;
            return;
        }
        colocarMina();
    }

    private void generarNumeros() {
        int cuenta;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] == MINA) { // Ignorar celdas con minas
                    continue;
                }

                cuenta = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) { // Saltar el centro de la celda
                            continue;
                        }

                        int newFila = i + di;
                        int newCol = j + dj;

                        if (esValido(newFila, newCol)) {
                            if (matriz[newFila][newCol] == MINA) {
                                cuenta++;
                            }
                        }
                    }
                }
                matriz[i][j] = (char) (cuenta + CERO);
            }
        }
    }

    private boolean esValido(int fila, int columna) {
        /* Asegurarse de que la celda evaluada esté dentro de la matriz para
        evitar OutOfBoundExceptions */
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
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
