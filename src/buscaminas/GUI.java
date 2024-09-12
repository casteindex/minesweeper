/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminas;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alejandro
 */
public class GUI {

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
                button.setPreferredSize(new Dimension(40, 40));
                minas_panel.add(button);
            }
        }

        frame.pack(); // JFrame ajustado al contenido
        frame.setLocationRelativeTo(null); // Centrar ventana al ejecutar
        frame.setVisible(true); // Mostrar JFrame después de agregar el contenido

    }

}
