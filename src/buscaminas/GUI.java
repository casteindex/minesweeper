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
    private final char CERO = '0';
    private boolean tableroBloqueado = false;

    private final ImageIcon tileIcon = new ImageIcon("./icons/T.png");
    private final ImageIcon flagIcon = new ImageIcon("./icons/F.png");
    private final ImageIcon mineIcon = new ImageIcon("./icons/M.png");
    private final ImageIcon redMineIcon = new ImageIcon("./icons/MR.png");
    private final ImageIcon xMineIcon = new ImageIcon("./icons/MX.png");
    private final ImageIcon[] numberIcons = new ImageIcon[9]; // Númeos del 0 al 8

    JFrame frame = new JFrame();
    JPanel minas_panel = new JPanel();
    JButton[][] buttons;

    // Constructor
    public GUI(TableroSecreto tableroSecreto) {
        this.matrizSecreta = tableroSecreto.getMatriz();
        this.filas = matrizSecreta.length;
        this.columnas = matrizSecreta[0].length;
        this.buttons = new JButton[filas][columnas]; // Inicializar buttons de botones

        // Cargar imágenes de números al array
        for (int i = 0; i < numberIcons.length; i++) {
            numberIcons[i] = new ImageIcon("./icons/" + i + ".png");
        }

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

    private void llenarButtonPanel() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                JButton button = new JButton();
                button.setFocusPainted(false); // Elimina rectángulo de enfoque
                button.setPreferredSize(new Dimension(35, 35));
                button.setIcon(tileIcon);

                buttons[i][j] = button;
                minas_panel.add(button);

                addClickEvents(button, i, j);
            }
        }
    }

    public void addClickEvents(JButton button, int fila, int columna) {

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) { // Click normal
                    if (!tableroBloqueado) {
                        descubrir(fila, columna);
                    }

                } else if (SwingUtilities.isRightMouseButton(e)) { // Click derecho
                    if (!tableroBloqueado) {
                        toggleBandera(button);
                    }
                }
            }
        });
    }

    public void toggleBandera(JButton button) {
        if (button.getIcon().equals(tileIcon)) {
            button.setIcon(flagIcon);
        } else if (button.getIcon().equals(flagIcon)) {
            button.setIcon(tileIcon);
        }
    }

    public void descubrir(int x, int y) {
        if (buttons[x][y].getIcon().equals(flagIcon)) {
            return;
        }
        if (matrizSecreta[x][y] == MINA) {
            gameOver();
            buttons[x][y].setIcon(redMineIcon);
            return;
        }

        // Mostrar imágen del número que corresponde al de la matrizSecreta
        buttons[x][y].setIcon(numberIcons[Character.getNumericValue(matrizSecreta[x][y])]);

        if (matrizSecreta[x][y] == CERO) {
            descubrirCeros(x, y);
        }
    }

    public void gameOver() {
        System.out.println("Game over");
        tableroBloqueado = true;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizSecreta[i][j] == MINA) {
                    if (!buttons[i][j].getIcon().equals(flagIcon)) {
                        buttons[i][j].setIcon(mineIcon);
                    }
                } else if (buttons[i][j].getIcon().equals(flagIcon)) {
                    buttons[i][j].setIcon(xMineIcon);
                }
            }
        }
    }

    private void descubrirCeros(int fila, int columna) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) { // Saltar el centro de la celda
                    continue;
                }
                int newFila = fila + di;
                int newCol = columna + dj;

                if (esValido(newFila, newCol) && buttons[newFila][newCol].getIcon().equals(tileIcon)) {
                    descubrir(newFila, newCol);
                }
            }
        }
    }

    private boolean esValido(int fila, int columna) {
        /* Asegurarse de que la celda evaluada esté dentro de la buttons para
        evitar OutOfBoundExceptions */
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

}
