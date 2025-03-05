package com.nhnacademy.manager;

import com.nhnacademy.shapes.Drawable;
import com.nhnacademy.shapes.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

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

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        VBox uiOverlay = new VBox();
        uiOverlay.getChildren().add(scoreLabel);
        uiOverlay.setStyle("-fx-alignment: top-center; -fx-padding: 10;");

        root = new StackPane();
        root.getChildren().addAll(canvas, uiOverlay);
    }

    public StackPane getRoot() {
        return root;
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

    public boolean isGameStop() {
        return gameStop;
    }
}