package com.example.miniproyecto_2.controllers;

import com.example.miniproyecto_2.models.Sudoku;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Sudoku new_sudoku;
    @FXML
    private TextField p_00,p_01,p_02,p_03,p_04,p_05,
            p_10,p_11,p_12,p_13,p_14,p_15,
            p_20,p_21,p_22,p_23,p_24,p_25,
            p_30,p_31,p_32,p_33,p_34,p_35,
            p_40,p_41,p_42,p_43,p_44,p_45,
            p_50,p_51,p_52,p_53,p_54,p_55;

    @FXML
    void onActionPlayButton(ActionEvent event) throws IOException {
        System.out.println("Play button pressed");
        new_sudoku = new Sudoku();
        new_sudoku.generateSudoku();
        System.out.println(new_sudoku.getSudoku());
        ArrayList<ArrayList<Integer>> sudokuMatrix = new_sudoku.getSudoku();

        // Crear un ArrayList de TextFields para mapear los TextFields con la matriz del Sudoku
        ArrayList<ArrayList<TextField>> textFields = new ArrayList<>();

        textFields.add(new ArrayList<>(List.of(p_00, p_01, p_02, p_03, p_04, p_05)));
        textFields.add(new ArrayList<>(List.of(p_10, p_11, p_12, p_13, p_14, p_15)));
        textFields.add(new ArrayList<>(List.of(p_20, p_21, p_22, p_23, p_24, p_25)));
        textFields.add(new ArrayList<>(List.of(p_30, p_31, p_32, p_33, p_34, p_35)));
        textFields.add(new ArrayList<>(List.of(p_40, p_41, p_42, p_43, p_44, p_45)));
        textFields.add(new ArrayList<>(List.of(p_50, p_51, p_52, p_53, p_54, p_55)));

        // Recorrer la matriz del Sudoku y asignar los valores a los TextFields
        for (int i = 0; i < sudokuMatrix.size(); i++) {
            for (int j = 0; j < sudokuMatrix.get(i).size(); j++) {
                textFields.get(i).get(j).setText(String.valueOf(sudokuMatrix.get(i).get(j)));
            }
        }
    }
}
