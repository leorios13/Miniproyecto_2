package com.example.miniproyecto_2.view.alert;

import javafx.scene.control.Alert;

/**
 * Clase que implementa la interfaz AlertBoxInterface y proporciona
 * métodos para mostrar alertas de información y error en la aplicación.
 */
public class AlertBox implements AlertBoxInterface {

    /**
     * Muestra una alerta de tipo información.
     *
     * @param title   El título de la alerta.
     * @param header  El encabezado de la alerta.
     * @param message El mensaje de contenido que se mostrará en la alerta.
     */
    @Override
    public void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Espera hasta que el usuario cierre la alerta
    }

    /**
     * Muestra una alerta de tipo error.
     *
     * @param title   El título de la alerta.
     * @param header  El encabezado de la alerta.
     * @param message El mensaje de contenido que se mostrará en la alerta.
     */
    public void showError(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Espera hasta que el usuario cierre la alerta
    }
}