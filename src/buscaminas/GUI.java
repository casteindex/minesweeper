/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Alejandro
 */
public class GUI {

    // Atributos
    private final int filas;
    private final int columnas;
    private final char[][] matrizSecreta;
    private final char MINA = 'M';
    private final char FLAG = 'F';
    private final char CERO = '0';
    private final char VACIO = ' ';

    JFrame frame = new JFrame();
    JPanel minas_panel = new JPanel();
    JButton[][] matriz;

    // Constructor
    public GUI(TableroSecreto tableroSecreto) {
        this.matrizSecreta = tableroSecreto.getMatriz();
        this.filas = matrizSecreta.length;
        this.columnas = matrizSecreta[0].length;
        this.matriz = new JButton[filas][columnas]; // Inicializar matriz de botones

        // JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(minas_panel);

        // Minas JPanel
        minas_panel.setLayout(new GridLayout(filas, columnas));
        llenarButtonPanel();

        frame.pack(); // JFrame ajustado al contenido
        frame.setLocationRelativeTo(null); // Centrar ventana al ejecutar
        frame.setVisible(true); // Mostrar JFrame después de agregar el contenido
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        for (int i = 0; i < filas; i++) {
//            for (int j = 0; j < columnas; j++) {
//                if (e.getSource() == matriz[i][j]) {
//                    if (!descubrir(i, j)) { // Devuelve si perdió
//                        System.out.println("Game Over!");
//                    }
//                }
//            }
//        }
//    }
    public boolean descubrir(int x, int y) {
        // Si tiene una bomba
        if (matrizSecreta[x][y] == MINA) {
            matriz[x][y].setText(Character.toString(MINA));
            return false; // Usuario perdió el juego
        }

        // Mostrar el numero de la matriz secreta
        matriz[x][y].setText(Character.toString(matrizSecreta[x][y]));

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

                if (esValido(newFila, newCol) && matriz[newFila][newCol].getText().isBlank()) {
                    matriz[newFila][newCol].setText(Character.toString(matrizSecreta[newFila][newCol]));

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

    private void llenarButtonPanel() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                JButton button = new JButton("");
                button.setFocusPainted(false); // Elimina rectángulo de enfoque
                button.setPreferredSize(new Dimension(45, 45));

                matriz[i][j] = button;
                minas_panel.add(button);

                /* Nota: para inner classes (clases anónimas) como el ActionListener,
                Java necesita que los parámetros sean finales (o "efectivamente finales")
                por lo que no se pueden usar `i` y `j` porque se siguen modificando
                en el for loop. Copiandolas, se hacen "efectivamente finales"*/
                final int fila = i;
                final int columna = j;

                // Click normal
                matriz[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!descubrir(fila, columna)) {
                            System.out.println("Game Over!");
                        }

                    }
                });
            }
        }
    }

}
