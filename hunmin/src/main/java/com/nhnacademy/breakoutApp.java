package com.nhnacademy;

import com.nhnacademy.controller.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class breakoutApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);

        // 레이아웃
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 800, 600);

        // GameManager 생성 및 초기화
        GameManager gameManager = new GameManager(canvas, canvas.getGraphicsContext2D());
        gameManager.startGame(); // AnimationTimer 시작

        // 키보드 이벤트 처리 -> gameManager에 위임
        scene.setOnKeyPressed(gameManager::onKeyPressed);
        scene.setOnKeyReleased(gameManager::onKeyReleased);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Breakout Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
