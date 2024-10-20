package com.example.miniproyecto_2;

import com.example.miniproyecto_2.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Leonardo Rios Saavedra
 * Código: 2310129
 * Clase principal para iniciar la aplicación de Sudoku.
 * Extiende la clase Application de JavaFX.
 */
public class Main extends Application {

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); // Llamar al método launch para iniciar JavaFX
    }

    /**
     * Método que se llama al iniciar la aplicación.
     *
     * @param primaryStage La ventana principal de la aplicación.
     * @throws IOException Si hay un error al cargar la vista del juego.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameView.getInstance(); // Obtener la instancia de GameView
    }
}
