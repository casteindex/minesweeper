/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminas;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alejandro
 */
public class GUI implements ActionListener {

    // Atributos
    int filas;
    int columnas;
    private final char[][] matrizSecreta;
    private static final char MINA = 'M';
    private static final char FLAG = 'F';
    private static final char CERO = '0';
    private static final char VACIO = ' ';

    JFrame frame = new JFrame();
    JPanel minas_panel = new JPanel();
    JButton[][] matriz;

    // Constructor
    public GUI(int filas, int columnas, TableroSecreto tableroSecreto) {
        this.filas = filas;
        this.columnas = columnas;
        this.matrizSecreta = tableroSecreto.getMatriz();
        this.matriz = new JButton[filas][columnas]; // Inicializar matriz de botones

        // JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(minas_panel);

        // Minas JPanel
        minas_panel.setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                JButton button = new JButton("");
                button.setFocusPainted(false); // Elimina rectángulo de enfoque
                button.setPreferredSize(new Dimension(45, 45));

                matriz[i][j] = button;
                minas_panel.add(button);

                matriz[i][j].addActionListener(this);
            }
        }

        frame.pack(); // JFrame ajustado al contenido
        frame.setLocationRelativeTo(null); // Centrar ventana al ejecutar
        frame.setVisible(true); // Mostrar JFrame después de agregar el contenido

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (e.getSource() == matriz[i][j]) {
                    descubrir(i, j);
//                    matriz[i][j].setText(Character.toString(matrizSecreta[i][j]));
                }
            }
        }

    }

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

}
