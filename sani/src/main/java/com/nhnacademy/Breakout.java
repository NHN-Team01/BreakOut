package com.nhnacademy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Breakout extends Application {

    private final GameController gameController = new GameController();

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(gameController.getRoot(), 800, 600);
        gameController.initializeGame();
        gameController.handleKeyInput(scene);
        gameController.startGameLoop();

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}