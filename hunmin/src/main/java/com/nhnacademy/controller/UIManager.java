package com.nhnacademy.controller;

import com.nhnacademy.view.Drawable;
import com.nhnacademy.view.Shape;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 게임의 UI를 그리는 클래스
 */
public class UIManager {
    private final Canvas canvas;
    private final GraphicsContext gc;

    public UIManager(Canvas canvas, GraphicsContext gc) {
        this.canvas = canvas;
        this.gc = gc;
    }

    public void renderBackground() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void renderShapes(List<Shape> shapes) {
        for (Shape shape : shapes) {
            ((Drawable) shape).draw(gc);
        }
    }

    public void renderPausedOverlay() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setFill(Color.rgb(0,0,0, 0.5));
        gc.fillRect(0, 0, w, h);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(40));
        String pausedText = "PAUSED";

        Text tempText = new Text(pausedText);
        tempText.setFont(gc.getFont());

        double textWidth = tempText.getLayoutBounds().getWidth();
        double textHeight = tempText.getLayoutBounds().getHeight();

        double x = (w - textWidth) / 2;
        double y = (h + textHeight) / 2;

        gc.fillText(pausedText, x, y);
    }

    public void showGameOverPopup(Runnable onRetry, Runnable onExit) {
        Platform.runLater(() -> {
            ButtonType retryButton = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.NONE, "Game Over! Thank you for playing.", retryButton, exitButton);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            alert.showAndWait().ifPresent(response -> {
                if (response == retryButton) {
                    onRetry.run();
                } else {
                    onExit.run();
                }
            });
        });
    }

}
