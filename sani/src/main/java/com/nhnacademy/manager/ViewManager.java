package com.nhnacademy.manager;

import com.nhnacademy.shapes.Drawable;
import com.nhnacademy.shapes.Shape;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class ViewManager {
    private Canvas canvas;
    private GraphicsContext gc;
    private Label scoreLabel;
    private StackPane root;
    private boolean gameStop;

    public ViewManager() {
        initializeUI();
    }

    private void initializeUI() {
        gameStop = false;
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        scoreLabel = createStyledLabel("Score: 0", "-fx-font-size: 20px; -fx-text-fill: white;");

        VBox uiOverlay = new VBox();
        uiOverlay.getChildren().add(scoreLabel);
        uiOverlay.setStyle("-fx-alignment: top-center; -fx-padding: 10;");

        root = new StackPane();
        root.getChildren().addAll(canvas, uiOverlay);
    }

    public void render(List<Shape> shapes, int score) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        scoreLabel.setText("Score: " + score);

        for (Shape shape : shapes) {
            if (shape instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }

    public void showGameOverPopup(int finalScore, Runnable onRestart) {
        gameStop = true;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Game Over");

            // 사용자 정의 메시지 및 스타일
            VBox content = new VBox(10);
            content.setAlignment(Pos.CENTER);

            Label gameOverLabel = createStyledLabel("Game Over!", "-fx-font-size: 24px; -fx-font-weight: bold;");

            Label scoreLabel = createStyledLabel("최종 점수:  " + finalScore , "-fx-font-size: 18px;");

            // 버튼 생성
            ButtonType restartButton = new ButtonType("다시 시작", ButtonBar.ButtonData.OK_DONE);
            ButtonType exitButton = new ButtonType("종료", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(restartButton, exitButton);

            content.getChildren().addAll(gameOverLabel, scoreLabel);
            alert.getDialogPane().setContent(content);

            // 버튼 응답 처리
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == restartButton) {
                    gameStop = false;
                    onRestart.run();
                } else if (result.get() == exitButton) {
                    Platform.exit();
                }
            }
        });
    }

    private Label createStyledLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    public StackPane getRoot() {
        return root;
    }

    public boolean isGameStop() {
        return gameStop;
    }
}