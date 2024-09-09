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
public class Tablero {

    // Atributos
    private final int filas;
    private final int columnas;
    private final int minas;
    private final char[][] matriz;
    private static final char BOMBA = 'B';

    // Constructor
    public Tablero(int filas, int columnas, int minas) {
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
        // Generar bombas
        for (int i = 0; i < minas; i++) {
            colocarMina();
        }
        generarNumeros();
    }

    private void colocarMina() {
        Random random = new Random();
        int fila = random.nextInt(filas);
        int columna = random.nextInt(columnas);
        if (matriz[fila][columna] != BOMBA) {
            matriz[fila][columna] = BOMBA;
            return;
        }
        colocarMina();
    }

    private void generarNumeros() {
        int cuenta;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Si la celda tiene una mina, saltarla
                if (matriz[i][j] == BOMBA) {
                    continue;
                }
                cuenta = 0;
                // Ciclo sobre celdas vecinas (di, dj) para [-1, 0, 1]
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        // Saltar el centro de la celda
                        if (di == 0 && dj == 0) {
                            continue;
                        }
                        /* Asegurarse de que la celda evaluada esté dentro de la
                        matriz para evitar OutOfBoundExceptions */
                        int filVecino = i + di;
                        int colVecino = j + dj;
                        if (filVecino >= 0 && filVecino < filas
                                && colVecino >= 0 && colVecino < columnas) {
                            if (matriz[filVecino][colVecino] == BOMBA) {
                                cuenta++;
                            }
                        }
                    }
                }
                matriz[i][j] = (char) (cuenta + '0');
            }
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
