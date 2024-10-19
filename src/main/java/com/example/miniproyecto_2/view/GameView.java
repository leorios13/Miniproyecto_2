package com.example.miniproyecto_2.view;

import com.example.miniproyecto_2.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {

    private GameController gameController;

    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/miniproyecto_2/game-view.fxml")
        );
        Parent root = loader.load();
        this.gameController = loader.getController();
        this.setTitle("SUDOKU 6x6");
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/miniproyecto_2/images/sudoku.png")
        ));
        this.setScene(scene);
        this.show();
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public static GameView getInstance() throws IOException {
        if (GameViewHolder.INSTANCE == null) {
            return GameViewHolder.INSTANCE = new GameView();
        } else{
            return GameViewHolder.INSTANCE;
        }

    }

    private static class GameViewHolder {
        private static GameView INSTANCE;
    }
}
