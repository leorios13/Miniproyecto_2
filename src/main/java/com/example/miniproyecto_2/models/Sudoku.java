package com.example.miniproyecto_2.models;

import java.util.ArrayList;
import java.util.Random;

public class Sudoku {
    private ArrayList<ArrayList<Integer>> sudoku_matrix;

    public Sudoku() {
        sudoku_matrix = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            ArrayList<Integer> sudoku_matrix_row = new ArrayList<>();
            for (int col = 0; col < 6; col++) {
                sudoku_matrix_row.add(0); //inicialmente llena de ceros
            }
            sudoku_matrix.add(sudoku_matrix_row);
        }
    }

    public boolean solveSudoku(){
        for (int i=0; i<sudoku_matrix.size(); i++){
            for (int j=0; j<sudoku_matrix.get(0).size(); j++){
                if (sudoku_matrix.get(i).get(j) == 0){
                    for (int value = 1; value <= 6; value++){
                        if(checkRow(i,value)&&checkColumn(j,value)&&checkQuadrant(i,j,value)){
                            sudoku_matrix.get(i).set(j, value);
                            if(solveSudoku()){
                                return true;
                            }
                            sudoku_matrix.get(i).set(j, 0);
                        }
                    }return false;
                }
            }
        }return true;
    }

    public boolean checkQuadrant(int i, int j, int value){
        int posI = subQuadrantCurrentRow(i);
        int posJ = subQuadrantCurrentColumn(j);

        for (int k=posI-2; k<posI; k++){
            for (int l=posJ-3; l<posJ; l++){
                if(sudoku_matrix.get(k).get(l) == value){
                    return false;
                }
            }
        }
        return true;
    }

    public int subQuadrantCurrentRow(int pos){
        if (pos < 2) return 2;
        else if (pos < 4) return 4;
        else return 6;
    }

    public int subQuadrantCurrentColumn(int pos){
        if (pos < 3) return 3;
        else return 6;
    }

    public boolean checkRow(int i, int value){
        for (int j=0; j<sudoku_matrix.get(i).size(); j++){
            if (sudoku_matrix.get(i).get(j) == value){
                return false;
            }
        }
        return true;
    }

    public boolean checkColumn(int j, int value){
        for (int i=0; i<sudoku_matrix.size(); i++){
            if (sudoku_matrix.get(i).get(j) == value){
                return false;
            }
        }
        return true;
    }

    public void cleanSudoku(){
        for (int i=0; i<sudoku_matrix.size(); i++){
            for (int j=0; j<sudoku_matrix.get(0).size(); j++){
                sudoku_matrix.get(i).set(j, 0);
            }
        }
    }

    public boolean hasZeros() {
        for (ArrayList<Integer> row : sudoku_matrix) {
            for (Integer value : row) {
                if (value == 0) {
                    return true; // Si encontramos un cero, significa que aún hay celdas vacías
                }
            }
        }
        return false; // Si no encontramos ceros, entonces la matriz está completa
    }
    public boolean hasRepeatedColumns() {
        for (int col = 0; col < 6; col++) {
            ArrayList<Integer> seenNumbers = new ArrayList<>();
            for (int row = 0; row < 6; row++) {
                int value = sudoku_matrix.get(row).get(col);
                if (value != 0) { // Ignorar celdas vacías
                    if (seenNumbers.contains(value)) {
                        return true; // Si el número ya fue visto, hay repetición en la columna
                    }
                    seenNumbers.add(value);
                }
            }
        }
        return false; // No hay columnas repetidas
    }
    public void generateSudoku(){
        while (hasZeros() || hasRepeatedColumns()) {
            cleanSudoku(); //limpiar matriz antes de generar un nuevo sudoku
            Random random = new Random();
            for (int i=0;i<2;i++){
                for (int j=0; j<3; j++){
                    int num = random.nextInt(6)+1;
                    if (checkQuadrant(i,j,num)){
                        sudoku_matrix.get(i).set(j, num);
                    }
                    else{
                        j--;
                    }
                }
            }

            for (int i=2;i<4;i++){
                for (int j=3; j<6; j++){
                    int num = random.nextInt(6)+1;
                    if (checkQuadrant(i,j,num)){
                        sudoku_matrix.get(i).set(j, num);
                    }
                    else{
                        j--;
                    }
                }
            }

            for (int i=4;i<6;i++){
                for (int j=0; j<3; j++){
                    int num = random.nextInt(6)+1;
                    if (checkQuadrant(i,j,num)){
                        sudoku_matrix.get(i).set(j, num);
                    }
                    else{
                        j--;
                    }
                }
            }

            solveSudoku();
        }



    }

    public ArrayList<ArrayList<Integer>> getSudoku() {
        return sudoku_matrix;
    }
    public void setSudoku(ArrayList<ArrayList<Integer>> sudoku_matrix) {
        this.sudoku_matrix = sudoku_matrix;
    }
}
