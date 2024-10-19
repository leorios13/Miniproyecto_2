package com.example.miniproyecto_2.models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa un juego de Sudoku de 6x6.
 * Esta clase contiene la lógica para generar, resolver y verificar el Sudoku.
 */
public class Sudoku {
    private ArrayList<ArrayList<Integer>> sudoku_matrix; // Matriz que representa el estado del Sudoku

    /**
     * Constructor de la clase Sudoku.
     * Inicializa la matriz de Sudoku con ceros.
     */
    public Sudoku() {
        sudoku_matrix = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            ArrayList<Integer> sudoku_matrix_row = new ArrayList<>();
            for (int col = 0; col < 6; col++) {
                sudoku_matrix_row.add(0); // Inicialmente llena de ceros
            }
            sudoku_matrix.add(sudoku_matrix_row);
        }
    }

    /**
     * Resuelve el Sudoku utilizando backtracking.
     *
     * @return true si el Sudoku se resuelve correctamente, false en caso contrario.
     */
    public boolean solveSudoku() {
        for (int i = 0; i < sudoku_matrix.size(); i++) {
            for (int j = 0; j < sudoku_matrix.get(0).size(); j++) {
                if (sudoku_matrix.get(i).get(j) == 0) {
                    for (int value = 1; value <= 6; value++) {
                        if (checkRow(i, value) && checkColumn(j, value) && checkQuadrant(i, j, value)) {
                            sudoku_matrix.get(i).set(j, value); // Asignar el valor
                            if (solveSudoku()) {
                                return true; // Si se resuelve con este valor, devolver true
                            }
                            sudoku_matrix.get(i).set(j, 0); // Deshacer la asignación
                        }
                    }
                    return false; // No se encontró un valor válido
                }
            }
        }
        return true; // Sudoku resuelto
    }

    /**
     * Verifica si el valor dado ya existe en el cuadrante correspondiente.
     *
     * @param i     La fila de la celda.
     * @param j     La columna de la celda.
     * @param value El valor a verificar.
     * @return true si el valor no está en el cuadrante, false en caso contrario.
     */
    public boolean checkQuadrant(int i, int j, int value) {
        int posI = subQuadrantCurrentRow(i);
        int posJ = subQuadrantCurrentColumn(j);

        for (int k = posI - 2; k < posI; k++) {
            for (int l = posJ - 3; l < posJ; l++) {
                if (sudoku_matrix.get(k).get(l) == value) {
                    return false; // Si el valor ya está en el cuadrante, devolver false
                }
            }
        }
        return true; // El valor no está en el cuadrante
    }

    /**
     * Devuelve la fila de inicio del cuadrante actual.
     *
     * @param pos La posición de la fila.
     * @return La fila de inicio del cuadrante.
     */
    public int subQuadrantCurrentRow(int pos) {
        if (pos < 2) return 2;
        else if (pos < 4) return 4;
        else return 6;
    }

    /**
     * Devuelve la columna de inicio del cuadrante actual.
     *
     * @param pos La posición de la columna.
     * @return La columna de inicio del cuadrante.
     */
    public int subQuadrantCurrentColumn(int pos) {
        if (pos < 3) return 3;
        else return 6;
    }

    /**
     * Verifica si el valor ya existe en la fila especificada.
     *
     * @param i     La fila a verificar.
     * @param value El valor a verificar.
     * @return true si el valor no está en la fila, false en caso contrario.
     */
    public boolean checkRow(int i, int value) {
        for (int j = 0; j < sudoku_matrix.get(i).size(); j++) {
            if (sudoku_matrix.get(i).get(j) == value) {
                return false; // Si el valor ya está en la fila, devolver false
            }
        }
        return true; // El valor no está en la fila
    }

    /**
     * Verifica si el valor ya existe en la columna especificada.
     *
     * @param j     La columna a verificar.
     * @param value El valor a verificar.
     * @return true si el valor no está en la columna, false en caso contrario.
     */
    public boolean checkColumn(int j, int value) {
        for (int i = 0; i < sudoku_matrix.size(); i++) {
            if (sudoku_matrix.get(i).get(j) == value) {
                return false; // Si el valor ya está en la columna, devolver false
            }
        }
        return true; // El valor no está en la columna
    }

    /**
     * Limpia la matriz del Sudoku estableciendo todos los valores en cero.
     */
    public void cleanSudoku() {
        for (int i = 0; i < sudoku_matrix.size(); i++) {
            for (int j = 0; j < sudoku_matrix.get(0).size(); j++) {
                sudoku_matrix.get(i).set(j, 0); // Establecer todos los valores a cero
            }
        }
    }

    /**
     * Verifica si hay celdas vacías en la matriz.
     *
     * @return true si hay al menos un cero, false si no hay ceros.
     */
    public boolean hasZeros() {
        for (ArrayList<Integer> row : sudoku_matrix) {
            for (Integer value : row) {
                if (value == 0) {
                    return true; // Si encontramos un cero, hay celdas vacías
                }
            }
        }
        return false; // No hay celdas vacías
    }

    /**
     * Verifica si hay números repetidos en las columnas.
     *
     * @return true si hay números repetidos, false en caso contrario.
     */
    public boolean hasRepeatedColumns() {
        for (int col = 0; col < 6; col++) {
            ArrayList<Integer> seenNumbers = new ArrayList<>();
            for (int row = 0; row < 6; row++) {
                int value = sudoku_matrix.get(row).get(col);
                if (value != 0) { // Ignorar celdas vacías
                    if (seenNumbers.contains(value)) {
                        return true; // Si el número ya fue visto, hay repetición en la columna
                    }
                    seenNumbers.add(value); // Añadir número a la lista
                }
            }
        }
        return false; // No hay columnas repetidas
    }

    /**
     * Genera un Sudoku aleatorio asegurando que no haya ceros ni columnas repetidas.
     */
    public void generateSudoku() {
        while (hasZeros() || hasRepeatedColumns()) {
            cleanSudoku(); // Limpiar matriz antes de generar un nuevo Sudoku
            Random random = new Random();

            // Llenar las celdas de los cuadrantes de 2x3 con números aleatorios
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    int num = random.nextInt(6) + 1;
                    if (checkQuadrant(i, j, num)) {
                        sudoku_matrix.get(i).set(j, num);
                    } else {
                        j--; // Intentar de nuevo en la misma posición
                    }
                }
            }

            for (int i = 2; i < 4; i++) {
                for (int j = 3; j < 6; j++) {
                    int num = random.nextInt(6) + 1;
                    if (checkQuadrant(i, j, num)) {
                        sudoku_matrix.get(i).set(j, num);
                    } else {
                        j--; // Intentar de nuevo en la misma posición
                    }
                }
            }

            for (int i = 4; i < 6; i++) {
                for (int j = 0; j < 3; j++) {
                    int num = random.nextInt(6) + 1;
                    if (checkQuadrant(i, j, num)) {
                        sudoku_matrix.get(i).set(j, num);
                    } else {
                        j--; // Intentar de nuevo en la misma posición
                    }
                }
            }

            solveSudoku(); // Resuelve el Sudoku para llenar el resto de la matriz
        }
    }

    /**
     * Obtiene la matriz de Sudoku.
     *
     * @return La matriz que representa el Sudoku.
     */
    public ArrayList<ArrayList<Integer>> getSudoku() {
        return sudoku_matrix;
    }

    /**
     * Establece la matriz de Sudoku.
     *
     * @param sudoku_matrix La nueva matriz de Sudoku a establecer.
     */
    public void setSudoku(ArrayList<ArrayList<Integer>> sudoku_matrix) {
        this.sudoku_matrix = sudoku_matrix;
    }
}
