package com.example.miniproyecto_2.view;

import com.example.miniproyecto_2.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que representa la vista del juego de Sudoku.
 * Extiende la clase Stage de JavaFX para crear una ventana de juego.
 */
public class GameView extends Stage {

    private GameController gameController; // Controlador del juego

    /**
     * Constructor de la clase GameView.
     * Carga el archivo FXML, establece la escena y configura la ventana.
     *
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_2/game-view.fxml")
        );
        Parent root = loader.load(); // Cargar la raíz desde el archivo FXML
        this.gameController = loader.getController(); // Obtener el controlador asociado
        this.setTitle("SUDOKU 6x6"); // Establecer el título de la ventana
        Scene scene = new Scene(root); // Crear la escena con la raíz cargada
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/miniproyecto_2/images/sudoku.png")
        )); // Establecer el icono de la ventana
        this.setScene(scene); // Establecer la escena en la ventana
        this.show(); // Mostrar la ventana
    }

    /**
     * Obtiene el controlador del juego asociado a esta vista.
     *
     * @return El GameController asociado.
     */
    public GameController getGameController() {
        return this.gameController; // Devolver el controlador
    }

    /**
     * Método estático para obtener la instancia única de GameView.
     *
     * @return La instancia de GameView.
     * @throws IOException Si hay un error al crear la instancia.
     */
    public static GameView getInstance() throws IOException {
        if (GameViewHolder.INSTANCE == null) {
            return GameViewHolder.INSTANCE = new GameView(); // Crear nueva instancia si no existe
        } else {
            return GameViewHolder.INSTANCE; // Devolver la instancia existente
        }
    }

    /**
     * Clase interna que implementa el patrón Singleton para GameView.
     */
    private static class GameViewHolder {
        private static GameView INSTANCE; // Instancia única de GameView
    }
}