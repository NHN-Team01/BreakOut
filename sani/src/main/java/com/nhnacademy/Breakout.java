package com.nhnacademy;

import com.nhnacademy.manager.ObjectManager;
import com.nhnacademy.manager.ViewManager;
import com.nhnacademy.shapes.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Breakout extends Application {
    private boolean moveLeft;
    private boolean moveRight;
    private AnimationTimer gameLoop;
    int score;

    private ViewManager viewManager;
    private final ObjectManager objectManager = new ObjectManager();

    private void initializeGame() {

        score = 0;
        moveLeft = false;
        moveRight = false;
        objectManager.initializeGameObjects();

    }

    @Override
    public void start(Stage primaryStage) {
        viewManager = new ViewManager();
        Scene scene = new Scene(viewManager.getRoot(), 800, 600);

        initializeGame();

        // 게임 루프
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (viewManager.isGameStop()) {
                    return;
                }

                if (moveLeft) {
                    objectManager.getPaddle().moveLeft();
                } else if (moveRight) {
                    objectManager.getPaddle().moveRight();
                } else {
                    objectManager.getPaddle().setDx(0);
                }

                List<Brick> bricksToRemove = new ArrayList<>();

                for (Shape shape : objectManager.getShapes()) {
                    if (shape instanceof Movable movable) {
                        movable.move();
                    }

                    if (shape instanceof Wall wall) {
                        objectManager.getPaddle().isCollisionDetected(wall);
                    }

                    if (shape != objectManager.getBall() && objectManager.getBall().isCollisionDetected(shape)) {
                        if (shape instanceof Brick brick) {
                            score += 10;
                            bricksToRemove.add(brick);
                        }
                        if (shape instanceof Blockable) {
                            viewManager.showGameOverPopup(score, Breakout.this::initializeGame);
                            return;
                        }
                    }
                }

                objectManager.removeAllByShape(bricksToRemove);

                viewManager.render(objectManager.getShapes(), score);
            }
        };
        gameLoop.start();

        // 키보드 입력 처리
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false;
            }
        });

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}