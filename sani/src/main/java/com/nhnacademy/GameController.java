// GameController.java
package com.nhnacademy;

import com.nhnacademy.manager.InputManager;
import com.nhnacademy.manager.ObjectManager;
import com.nhnacademy.manager.ViewManager;
import com.nhnacademy.shapes.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final ObjectManager objectManager;
    private final ViewManager viewManager;
    private final InputManager inputManager;
    private int score;

    public GameController() {
        this.objectManager = new ObjectManager();
        this.viewManager = new ViewManager();
        this.inputManager = new InputManager();
        this.score = 0;
    }

    public void initializeGame() {
        score = 0;
        objectManager.initializeGameObjects();
        inputManager.reset();
    }

    public void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (viewManager.isGameStop()) {
                    return;
                }

                updatePaddleMovement();
                updateGameObjects();
                checkCollisions();
                checkGameState();
                renderGame();
            }
        };
        gameLoop.start();
    }

    private void updatePaddleMovement() {
        if (inputManager.isMoveLeft()) {
            objectManager.getPaddle().moveLeft();
        } else if (inputManager.isMoveRight()) {
            objectManager.getPaddle().moveRight();
        } else {
            objectManager.getPaddle().setDx(0);
        }
    }

    private void updateGameObjects() {
        for (Shape shape : objectManager.getShapes()) {
            if (shape instanceof Movable movable) {
                movable.move();
            }
        }
    }

    private void checkCollisions() {
        List<Brick> bricksToRemove = new ArrayList<>();

        for (Shape shape : objectManager.getShapes()) {
            if (shape instanceof Wall wall) {
                objectManager.getPaddle().isCollisionDetected(wall);
            }

            if (shape != objectManager.getBall() && objectManager.getBall().isCollisionDetected(shape)) {
                if (shape instanceof Brick brick) {
                    score += 10;
                    bricksToRemove.add(brick);
                }
                if (shape instanceof Blockable) {
                    viewManager.showGameEndPopup(score, this::initializeGame, "Game Over", "Game Over!");
                    return;
                }
            }
        }

        objectManager.removeAllByShape(bricksToRemove);
    }

    private void checkGameState() {
        boolean hasBrick = objectManager.getShapes().stream().anyMatch(shape -> shape instanceof Brick);

        if (!hasBrick) {
            viewManager.showGameEndPopup(score, this::initializeGame,  "Victory", "Victory!");
        }
    }

    public void handleKeyInput(Scene scene) {
        inputManager.handleKeyInput(scene);
    }

    private void renderGame() {
        viewManager.render(objectManager.getShapes(), score);
    }

    public Parent getRoot() {
        return viewManager.getRoot();
    }
}