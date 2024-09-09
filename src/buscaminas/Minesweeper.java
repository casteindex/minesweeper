/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminas;

import java.util.Random;

/**
 *
 * @author Alejandro
 */
public class Minesweeper {

    public static Random random = new Random();
    public static char[][] tablero = new char[9][9];

    public static void main(String[] args) {

        Tablero tablero = new Tablero(9, 9, 10);
        tablero.randomize();
        tablero.print();

    }

}
