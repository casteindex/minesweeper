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

    private int minasRestantes;
    private int celdasReveladas = 0;
    private boolean tableroBloqueado = false;

    private final ImageIcon tileIcon = new ImageIcon("./icons/T.png");
    private final ImageIcon flagIcon = new ImageIcon("./icons/F.png");
    private final ImageIcon mineIcon = new ImageIcon("./icons/M.png");
    private final ImageIcon redMineIcon = new ImageIcon("./icons/MR.png");
    private final ImageIcon xMineIcon = new ImageIcon("./icons/MX.png");

    private final ImageIcon happyIcon = new ImageIcon("./icons/face-happy.png");
    private final ImageIcon surpriseIcon = new ImageIcon("./icons/surprise-happy.png");
    private final ImageIcon glassesIcon = new ImageIcon("./icons/glasses-happy.png");
    private final ImageIcon deadIcon = new ImageIcon("./icons/dead-happy.png");

    private final ImageIcon[] numberIcons = new ImageIcon[9]; // Númeos del 0 al 8

    JFrame frame = new JFrame();
    JPanel minas_panel = new JPanel();
    JButton[][] buttons;

    private JLabel minesLeftLabel;
    private JButton faceButton;
    private JLabel timerLabel;

    // Constructor
    public GUI(TableroSecreto tableroSecreto) {
        this.matrizSecreta = tableroSecreto.getMatriz();
        this.filas = tableroSecreto.getFilas();
        this.columnas = tableroSecreto.getColumnas();
        this.minasRestantes = tableroSecreto.getMinas();
        this.buttons = new JButton[filas][columnas]; // Inicializar buttons de botones

        // Cargar imágenes de números al array
        for (int i = 0; i < numberIcons.length; i++) {
            numberIcons[i] = new ImageIcon("./icons/" + i + ".png");
        }

        // JFrame
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Info JPanel
        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH); // Pegar arriba

        // Minas JPanel
        minas_panel.setLayout(new GridLayout(filas, columnas));
        llenarButtonPanel();
        frame.add(minas_panel);

        frame.pack(); // JFrame ajustado al contenido
        frame.setLocationRelativeTo(null); // Centrar ventana al ejecutar
        frame.setVisible(true); // Mostrar JFrame
    }

    private void llenarButtonPanel() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                JButton button = new JButton();
                button.setFocusPainted(false); // Elimina rectángulo de enfoque
                button.setPreferredSize(new Dimension(34, 34));
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
            juegoPerdido();
            buttons[x][y].setIcon(redMineIcon);
            return;
        }

        // Mostrar imágen del número que corresponde al de la matrizSecreta
        buttons[x][y].setIcon(numberIcons[Character.getNumericValue(matrizSecreta[x][y])]);

        if (matrizSecreta[x][y] == CERO) {
            descubrirCeros(x, y);
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

    public void juegoPerdido() {
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

    public void juegoGanado() {
        // Mostrar una 
        System.out.println("Ganaste");
        tableroBloqueado = true;
    }

    private JPanel createTopPanel() {
        // Se necesita un panel como wrapper para centrar el topPanel horizontalmente
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear el topPanel con ancho fijo
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(320, 60));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // Layout horizontal

        // Minas restantes (lado izquierdo)
        minesLeftLabel = new JLabel("Minas: 10", SwingConstants.CENTER);
        minesLeftLabel.setPreferredSize(new Dimension(135, 60));
        minesLeftLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Boton de cara (centro)
        faceButton = new JButton(new ImageIcon("./icons/face-happy.png"));
        faceButton.setPreferredSize(new Dimension(50, 50));
        faceButton.setMinimumSize(new Dimension(50, 50));
        faceButton.setMaximumSize(new Dimension(50, 50));
        faceButton.setFocusPainted(false);

        // Tiempo (lado derecho)
        timerLabel = new JLabel("Time: 0", SwingConstants.CENTER);
        timerLabel.setPreferredSize(new Dimension(135, 60));
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Añadir componentes al topPanel
        topPanel.add(minesLeftLabel);
        topPanel.add(Box.createHorizontalGlue()); // Empujar botón al centro
        topPanel.add(faceButton);
        topPanel.add(Box.createHorizontalGlue()); // Empujar tiempo a la derecha
        topPanel.add(timerLabel);

        // Añadir el topPanel al centro del mainPanel
        mainPanel.add(topPanel, BorderLayout.CENTER);
        return mainPanel;
    }

}
