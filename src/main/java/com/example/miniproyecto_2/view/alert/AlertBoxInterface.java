package com.example.miniproyecto_2.view.alert;

/**
 * Interfaz que define los métodos para mostrar alertas en la aplicación.
 */
public interface AlertBoxInterface {

    /**
     * Muestra una alerta de tipo información.
     *
     * @param title   El título de la alerta.
     * @param header  El encabezado de la alerta.
     * @param message El mensaje de contenido que se mostrará en la alerta.
     */
    public void showAlert(String title, String header, String message);

    /**
     * Muestra una alerta de tipo error.
     *
     * @param title   El título de la alerta.
     * @param header  El encabezado de la alerta.
     * @param message El mensaje de contenido que se mostrará en la alerta.
     */
    public void showError(String title, String header, String message);
}

