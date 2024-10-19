module com.example.miniproyecto_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.miniproyecto_2 to javafx.fxml;
    opens com.example.miniproyecto_2.controllers to javafx.fxml;
    exports com.example.miniproyecto_2;
}