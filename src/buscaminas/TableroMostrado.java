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
    private final TableroSecreto matrizSecreta;

    // Constructor
    public TableroMostrado(int filas, int columnas, TableroSecreto matrizSecreta) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new char[filas][columnas];
        this.matrizSecreta = matrizSecreta;
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

    public TableroSecreto getMatrizSecreta() {
        return matrizSecreta;
    }

    // Métodos
    /* Crear los métodos para colocar banderas, para revelar numeros de celda
    y el metodo recursivo para mostrar las celdas que tienen cero (en la secreta)*/
    
    public void setVacio() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = ' ';
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
