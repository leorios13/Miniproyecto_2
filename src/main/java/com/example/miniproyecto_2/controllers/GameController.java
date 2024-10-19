package com.example.miniproyecto_2.controllers;

import com.example.miniproyecto_2.models.Sudoku;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        // Restablecer el estado de todos los TextFields (limpiar colores y desbloquear)
        resetBoard();

        new_sudoku = new Sudoku();
        new_sudoku.generateSudoku(); // Genera el sudoku completo
        ArrayList<ArrayList<Integer>> sudokuMatrix = new_sudoku.getSudoku();

        // Crear un ArrayList de TextFields para mapear los TextFields con la matriz del Sudoku
        ArrayList<ArrayList<TextField>> textFields = new ArrayList<>();

        textFields.add(new ArrayList<>(List.of(p_00, p_01, p_02, p_03, p_04, p_05)));
        textFields.add(new ArrayList<>(List.of(p_10, p_11, p_12, p_13, p_14, p_15)));
        textFields.add(new ArrayList<>(List.of(p_20, p_21, p_22, p_23, p_24, p_25)));
        textFields.add(new ArrayList<>(List.of(p_30, p_31, p_32, p_33, p_34, p_35)));
        textFields.add(new ArrayList<>(List.of(p_40, p_41, p_42, p_43, p_44, p_45)));
        textFields.add(new ArrayList<>(List.of(p_50, p_51, p_52, p_53, p_54, p_55)));

        // Mostrar 2 números por cuadrante de 2x3 aleatoriamente y bloquear esos TextFields
        Random random = new Random();
        ArrayList<Integer> visiblePositions;

        for (int rowQuadrant = 0; rowQuadrant < 6; rowQuadrant += 2) {
            for (int colQuadrant = 0; colQuadrant < 6; colQuadrant += 3) {
                visiblePositions = new ArrayList<>();
                // Elegir aleatoriamente 2 posiciones únicas en cada cuadrante
                while (visiblePositions.size() < 2) {
                    int pos = random.nextInt(6); // 0-5 (cuadrante de 2x3)
                    if (!visiblePositions.contains(pos)) {
                        visiblePositions.add(pos);
                    }
                }

                for (int k = 0; k < 6; k++) {
                    int row = rowQuadrant + k / 3; // Calcula la fila en el cuadrante
                    int col = colQuadrant + k % 3; // Calcula la columna en el cuadrante

                    if (visiblePositions.contains(k)) {
                        // Mostrar valor y bloquear el TextField
                        textFields.get(row).get(col).setText(String.valueOf(sudokuMatrix.get(row).get(col)));
                        textFields.get(row).get(col).setEditable(false);
                        textFields.get(row).get(col).setStyle("-fx-background-color: lightgray;");
                    } else {
                        // Dejar vacío y habilitar el TextField para que el usuario pueda ingresar
                        textFields.get(row).get(col).setText("");
                        textFields.get(row).get(col).setEditable(true);
                        final int r = row, c = col;

                        // Agregar listener para verificar el valor ingresado por el usuario
                        textFields.get(r).get(c).textProperty().addListener((observable, oldValue, newValue) -> {
                            if (!newValue.isEmpty()) {
                                try {
                                    int value = Integer.parseInt(newValue);
                                    if (value == sudokuMatrix.get(r).get(c)) {
                                        // Si el valor es correcto, quitar el borde rojo
                                        textFields.get(r).get(c).setBorder(null);
                                        checkIfSolved(textFields, sudokuMatrix); // Verificar si el sudoku está resuelto
                                    } else {
                                        // Si el valor es incorrecto, poner borde rojo
                                        textFields.get(r).get(c).setBorder(new Border(new BorderStroke(Color.RED,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    }
                                } catch (NumberFormatException e) {
                                    // Si el valor no es un número válido, poner borde rojo
                                    textFields.get(r).get(c).setBorder(new Border(new BorderStroke(Color.RED,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                }
                            } else {
                                // Si el campo queda vacío nuevamente, quitar el borde rojo
                                textFields.get(r).get(c).setBorder(null);
                            }
                        });
                    }
                }
            }
        }
    }

    private void resetBoard() {
        // Crear un ArrayList de todos los TextFields
        List<TextField> allTextFields = List.of(
                p_00, p_01, p_02, p_03, p_04, p_05,
                p_10, p_11, p_12, p_13, p_14, p_15,
                p_20, p_21, p_22, p_23, p_24, p_25,
                p_30, p_31, p_32, p_33, p_34, p_35,
                p_40, p_41, p_42, p_43, p_44, p_45,
                p_50, p_51, p_52, p_53, p_54, p_55
        );

        // Recorrer cada TextField y restablecer su estilo y estado
        for (TextField textField : allTextFields) {
            textField.setText(""); // Limpiar el texto
            textField.setEditable(true); // Hacer editable por defecto
            textField.setStyle(""); // Restablecer el estilo por defecto
            textField.setBorder(null); // Quitar cualquier borde de error
        }
    }

    // Método para verificar si el Sudoku está completamente resuelto
    private void checkIfSolved(ArrayList<ArrayList<TextField>> textFields, ArrayList<ArrayList<Integer>> sudokuMatrix) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String userInput = textFields.get(i).get(j).getText();
                if (userInput.isEmpty() || Integer.parseInt(userInput) != sudokuMatrix.get(i).get(j)) {
                    return; // Si hay un error o una casilla vacía, no hacer nada
                }
            }
        }

        // Si el Sudoku está resuelto correctamente, mostrar mensaje de ganador
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("¡Has resuelto el Sudoku correctamente!");
        alert.showAndWait();
    }
}