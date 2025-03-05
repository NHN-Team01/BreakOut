package com.nhnacademy.legacy;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWithExitPopup extends Application {
    private boolean gameFinished = false; // 게임 완료 여부를 나타내는 플래그

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 게임 루프
        AnimationTimer gameLoop = new AnimationTimer() {
            int frameCount = 0; // 프레임 카운트 (조건 체크를 위한 예제)

            @Override
            public void handle(long now) {
                if (gameFinished) {
                    return; // 게임이 종료되었으면 루프 중단
                }

                // 화면 초기화
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // 조건 체크: 예를 들어, 특정 프레임에서 게임 완료 처리
                frameCount++;
                if (frameCount > 300) { // 300프레임 이후 게임 종료 조건
                    gameFinished = true; // 게임 완료 플래그 설정
                    showGameOverPopup(); // 팝업 출력
                }

                // 게임 화면 예제 (원 그리기)
                gc.setFill(Color.WHITE);
                gc.fillOval(390, 290, 20, 20);
            }
        };

        gameLoop.start();

        // 레이아웃 설정
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Game with Exit Popup");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 팝업을 표시하고 종료하는 메서드
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Over! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
