package com.example.miniproyecto_2.controllers;

import com.example.miniproyecto_2.models.Sudoku;
import com.example.miniproyecto_2.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

/**
 * Controlador del juego de Sudoku.
 * Esta clase maneja la lógica del juego, incluidos los eventos de botones,
 * la generación de Sudokus y la verificación de respuestas.
 */
public class GameController {
    private Sudoku new_sudoku; // Objeto Sudoku que representa el juego actual
    private int cont_ayuda = 0;
    @FXML
    // Definición de los TextFields que representan las celdas del Sudoku
    private TextField p_00, p_01, p_02, p_03, p_04, p_05,
            p_10, p_11, p_12, p_13, p_14, p_15,
            p_20, p_21, p_22, p_23, p_24, p_25,
            p_30, p_31, p_32, p_33, p_34, p_35,
            p_40, p_41, p_42, p_43, p_44, p_45,
            p_50, p_51, p_52, p_53, p_54, p_55;

    /**
     * Maneja el evento del botón "Jugar".
     * Muestra una alerta de confirmación y, si se confirma, inicializa un nuevo juego de Sudoku.
     *
     * @param event El evento del botón que se activa al presionar "Jugar".
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @FXML
    void onActionPlayButton(ActionEvent event) throws IOException {
        cont_ayuda = 4;
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de nuevo juego");
        alert.setHeaderText(null);
        alert.setContentText("¿Desea empezar un juego nuevo?");

        // Botones "Sí" y "No"
        ButtonType buttonYes = new ButtonType("Sí");
        ButtonType buttonNo = new ButtonType("No");

        // Añadir los botones a la alerta
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        // Mostrar la alerta y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonYes) {
                // Si elige "Sí", comienza un nuevo juego
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
                                textFields.get(row).get(col).setStyle("-fx-background-color: lightgray; -fx-border-color: transparent; -fx-focus-color: transparent;");
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

                                            // Verificar si el número está entre 1 y 6
                                            if (value < 1 || value > 6) {
                                                // Si el número no está en el rango de 1 a 6, mostrar un mensaje de error
                                                new AlertBox().showError(
                                                        "Entrada no válida",
                                                        null,
                                                        "Por favor, ingrese solo números entre 1 y 6."
                                                );
                                                // Restablecer a su estado anterior
                                                textFields.get(r).get(c).setText(oldValue);
                                                textFields.get(r).get(c).setBorder(new Border(new BorderStroke(Color.RED,
                                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            } else if (value == sudokuMatrix.get(r).get(c)) {
                                                // Si el valor es correcto, quitar el borde rojo
                                                textFields.get(r).get(c).setBorder(null);
                                                checkIfSolved(textFields, sudokuMatrix); // Verificar si el sudoku está resuelto
                                            } else {
                                                // Si el valor es incorrecto, poner borde rojo
                                                textFields.get(r).get(c).setBorder(new Border(new BorderStroke(Color.RED,
                                                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            }
                                        } catch (NumberFormatException e) {
                                            // Si el valor no es un número válido, mostrar un mensaje de error
                                            new AlertBox().showError(
                                                    "Entrada no válida",
                                                    null,
                                                    "Por favor, ingrese solo números entre 1 y 6."
                                            );
                                            // Limpiar el campo o restablecer a su estado anterior
                                            textFields.get(r).get(c).setText(oldValue);
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
            } else {
                // Si elige "No", no se genera un nuevo juego
                System.out.println("El usuario decidió no iniciar un nuevo juego.");
            }
        });
    }

    /**
     * Restablece el tablero de Sudoku.
     * Limpia todos los TextFields, los hace editables y elimina cualquier estilo de error.
     */
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

    /**
     * Verifica si el Sudoku ha sido completamente resuelto.
     *
     * @param textFields La lista de TextFields que representan el tablero de Sudoku.
     * @param sudokuMatrix La matriz de Sudoku que contiene los valores correctos.
     */
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
        new AlertBox().showAlert(
                "Ganaste",
                "¡Felicidades!",
                "¡Has resuelto el sudoku correctamente!"
        );
    }

    /**
     * Maneja el evento del botón "Ayuda".
     * Sugiere un número para una celda vacía aleatoria del Sudoku.
     *
     * @param event El evento del botón que se activa al presionar "Ayuda".
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @FXML
    void OnActionHelpButton(ActionEvent event) throws IOException {
        if (cont_ayuda>=1){
            cont_ayuda--;
            // Crear un ArrayList de todos los TextFields y el Sudoku generado
            ArrayList<ArrayList<TextField>> textFields = new ArrayList<>();
            textFields.add(new ArrayList<>(List.of(p_00, p_01, p_02, p_03, p_04, p_05)));
            textFields.add(new ArrayList<>(List.of(p_10, p_11, p_12, p_13, p_14, p_15)));
            textFields.add(new ArrayList<>(List.of(p_20, p_21, p_22, p_23, p_24, p_25)));
            textFields.add(new ArrayList<>(List.of(p_30, p_31, p_32, p_33, p_34, p_35)));
            textFields.add(new ArrayList<>(List.of(p_40, p_41, p_42, p_43, p_44, p_45)));
            textFields.add(new ArrayList<>(List.of(p_50, p_51, p_52, p_53, p_54, p_55)));

            ArrayList<ArrayList<Integer>> sudokuMatrix = new_sudoku.getSudoku();

            // Crear una lista de las celdas vacías donde el usuario necesita sugerencias
            List<TextField> emptyCells = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (textFields.get(i).get(j).getText().isEmpty()) {
                        emptyCells.add(textFields.get(i).get(j));
                    }
                }
            }

            if (emptyCells.isEmpty()) {
                // Si no hay celdas vacías, mostrar un mensaje de que no hay ayuda disponible
                new AlertBox().showAlert(
                        "Sin ayuda disponible",
                        null,
                        "Se le acabaron las ayudas"
                );
                return;
            }

            // Elegir aleatoriamente una celda vacía para sugerir el número correcto
            Random random = new Random();
            TextField randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

            // Encontrar la posición de la celda en la matriz y mostrar el número correcto
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (textFields.get(i).get(j) == randomEmptyCell) {
                        // Mostrar el número correcto en la celda vacía seleccionada
                        randomEmptyCell.setText(String.valueOf(sudokuMatrix.get(i).get(j)));
                        randomEmptyCell.setEditable(false);
                        randomEmptyCell.setStyle("-fx-background-color: lightgreen;"); // Colorear la celda sugerida en verde
                        return;
                    }
                }
            }
        }
        else{
            new AlertBox().showAlert(
                    "Sin ayuda disponible",
                    null,
                    "Se le acabaron las ayudas"
            );
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param title El título de la alerta de error.
     * @param message El mensaje de error que se mostrará al usuario.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}