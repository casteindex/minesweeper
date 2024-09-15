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

    JFrame frame = new JFrame();
    JPanel minas_panel = new JPanel();
    JButton[][] buttons;

    private JLabel minasLabel;
    private JButton faceButton;
    private JLabel tiempoLabel;

    // Constantes (Iconos)
    private final ImageIcon TileIcon = new ImageIcon("./icons/T.png");
    private final ImageIcon FlagIcon = new ImageIcon("./icons/F.png");
    private final ImageIcon MinaIcon = new ImageIcon("./icons/M.png");
    private final ImageIcon MinaRojaIcon = new ImageIcon("./icons/MR.png");
    private final ImageIcon MinaXIcon = new ImageIcon("./icons/MX.png");

    private final ImageIcon FelizIcon = new ImageIcon("./icons/face-happy.png");
    private final ImageIcon SopresaIcon = new ImageIcon("./icons/face-surprise.png");
    private final ImageIcon LentesIcon = new ImageIcon("./icons/face-glasses.png");
    private final ImageIcon MuertoIcon = new ImageIcon("./icons/face-dead.png");

    private final ImageIcon[] numerosIcons = new ImageIcon[9]; // Númeos del 0 al 8

    // Constructor
    public GUI(TableroSecreto tableroSecreto) {
        this.matrizSecreta = tableroSecreto.getMatriz();
        this.filas = tableroSecreto.getFilas();
        this.columnas = tableroSecreto.getColumnas();
        this.minasRestantes = tableroSecreto.getMinas();
        this.buttons = new JButton[filas][columnas]; // Inicializar buttons de botones

        // Cargar imágenes de números al array
        for (int i = 0; i < numerosIcons.length; i++) {
            numerosIcons[i] = new ImageIcon("./icons/" + i + ".png");
        }

        // JFrame
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setTitle("Buscaminas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Info JPanel
        JPanel topPanel = crearPanelSuperior();
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
                button.setPreferredSize(new Dimension(35, 35));
                button.setIcon(TileIcon);

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
                        faceButton.setIcon(SopresaIcon);
                        descubrir(fila, columna);
                    }

                } else if (SwingUtilities.isRightMouseButton(e)) { // Click derecho
                    if (!tableroBloqueado) {
                        toggleBandera(button);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!tableroBloqueado) {
                    faceButton.setIcon(FelizIcon);
                }
            }
        });
    }

    public void toggleBandera(JButton button) {
        if (button.getIcon().equals(TileIcon)) {
            button.setIcon(FlagIcon);
            minasRestantes--;
        } else if (button.getIcon().equals(FlagIcon)) {
            button.setIcon(TileIcon);
            minasRestantes++;
        }
        minasLabel.setText("Minas: " + minasRestantes);
    }

    public void descubrir(int x, int y) {
        if (buttons[x][y].getIcon().equals(FlagIcon)) {
            return;
        }
        if (matrizSecreta[x][y] == MINA) {
            juegoPerdido();
            buttons[x][y].setIcon(MinaRojaIcon);
            return;
        }

        // Mostrar imágen del número que corresponde al de la matrizSecreta
        buttons[x][y].setIcon(numerosIcons[Character.getNumericValue(matrizSecreta[x][y])]);

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

                if (esValido(newFila, newCol) && buttons[newFila][newCol].getIcon().equals(TileIcon)) {
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
        faceButton.setIcon(MuertoIcon);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizSecreta[i][j] == MINA) {
                    if (!buttons[i][j].getIcon().equals(FlagIcon)) {
                        buttons[i][j].setIcon(MinaIcon);
                    }
                } else if (buttons[i][j].getIcon().equals(FlagIcon)) {
                    buttons[i][j].setIcon(MinaXIcon);
                }
            }
        }
    }

    public void juegoGanado() {
        // Mostrar una 
        System.out.println("Ganaste");
        faceButton.setIcon(LentesIcon);
        tableroBloqueado = true;
    }

    private JPanel crearPanelSuperior() {
        // Se necesita un panel como wrapper para centrar el panelSuperior horizontalmente
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear el topPanel con ancho fijo
        JPanel panelSUperior = new JPanel();
        panelSUperior.setPreferredSize(new Dimension(320, 60));
        panelSUperior.setLayout(new BoxLayout(panelSUperior, BoxLayout.X_AXIS)); // Layout horizontal

        // Minas restantes (lado izquierdo)
        minasLabel = new JLabel("Minas: " + minasRestantes, SwingConstants.CENTER);
        minasLabel.setPreferredSize(new Dimension(135, 60));
        minasLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Boton de cara (centro)
        faceButton = new JButton(FelizIcon);
        faceButton.setPreferredSize(new Dimension(50, 50));
        faceButton.setMinimumSize(new Dimension(50, 50));
        faceButton.setMaximumSize(new Dimension(50, 50));
        faceButton.setFocusPainted(false);

        // Tiempo (lado derecho)
        tiempoLabel = new JLabel("Tiempo: 0", SwingConstants.CENTER);
        tiempoLabel.setPreferredSize(new Dimension(135, 60));
        tiempoLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Añadir componentes al topPanel
        panelSUperior.add(minasLabel);
        panelSUperior.add(Box.createHorizontalGlue()); // Empujar botón al centro
        panelSUperior.add(faceButton);
        panelSUperior.add(Box.createHorizontalGlue()); // Empujar tiempo a la derecha
        panelSUperior.add(tiempoLabel);

        // Añadir el topPanel al centro del mainPanel
        mainPanel.add(panelSUperior, BorderLayout.CENTER);
        return mainPanel;
    }

}
