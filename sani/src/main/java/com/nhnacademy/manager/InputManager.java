package com.nhnacademy.manager;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputManager {

    private boolean moveLeft;
    private boolean moveRight;

    public InputManager() {
        moveLeft = false;
        moveRight = false;
    }

    public void handleKeyInput(Scene scene) {
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
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void reset() {
        moveLeft = false;
        moveRight = false;
    }
}
