package com.nhnacademy.controller;


import com.nhnacademy.domain.data.GameState;
import com.nhnacademy.domain.data.Level;
import com.nhnacademy.domain.data.LevelType;
import com.nhnacademy.domain.model.Ball;
import com.nhnacademy.domain.model.Brick;
import com.nhnacademy.domain.model.Collidable;
import com.nhnacademy.domain.model.Moveable;
import com.nhnacademy.domain.model.Paddle;
import com.nhnacademy.view.Shape;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GameManager {

    private final Canvas canvas;
    private final UIManager uiManager;

    private AnimationTimer gameLoop;
    private boolean gameOver = false;

    private GameState currentGameState = GameState.PLAYING;
    private List<Shape> shapes;
    private Ball ball;
    private Paddle paddle;

    public GameManager(Canvas canvas, GraphicsContext graphicsContext) {
        this.canvas = canvas;
        this.uiManager = new UIManager(canvas, graphicsContext);
        initGameObjects();
    }

    private void initGameObjects() {
        Level currentLevel = LevelFactory.createLevel(LevelType.LEVEL_1, canvas.getWidth(), canvas.getHeight());
        ball = currentLevel.getBall();
        paddle = currentLevel.getPaddle();
        shapes = currentLevel.getShapes();
    }

    public void startGame() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) {
                    return;
                }
                update();
                render();
                handleGameState();
            }
        };
        gameLoop.start();
    }

    private void update() {
        for(Shape s : shapes) {
            if(s instanceof Ball b){
                if(b.isDead()){
                    gameOver = true;
                    uiManager.showGameOverPopup(
                            this::restartGame,
                            Platform::exit
                    );
                    return;
                }
            }
            if(s instanceof Moveable m){
                m.move();
            }
            if(s instanceof Collidable c){
                if(c.isCollisionDetected(ball)){
                    c.onCollision(ball);
                }
            }
        }
        shapes.removeIf(shape -> shape instanceof Brick && ((Brick) shape).isBroken());
    }

    private void handleGameState() {
        switch (currentGameState) {
            case PLAYING:
                for(Shape s : shapes) {
                    if(s instanceof Moveable m){
                        m.resume();
                    }
                }
                break;
            case PAUSED:
                for(Shape s : shapes) {
                    if(s instanceof Moveable m){
                        m.pause();
                    }
                }
                break;
        }
    }

    private void render() {
        uiManager.renderBackground();
        uiManager.renderShapes(shapes);
        if (currentGameState == GameState.PAUSED) {
            uiManager.renderPausedOverlay();
        }
    }

    public void restartGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        gameOver = false;
        initGameObjects();
        startGame();
    }

    public void onKeyPressed(KeyEvent e) {
        switch(e.getCode()) {
            case LEFT -> paddle.setMoveLeft(true);
            case RIGHT -> paddle.setMoveRight(true);
            case UP -> paddle.setMoveUp(true);
            case DOWN -> paddle.setMoveDown(true);
            case ESCAPE -> currentGameState = currentGameState.togglePlayPause();
        }
    }

    public void onKeyReleased(KeyEvent e){
        switch (e.getCode()) {
            case LEFT -> paddle.setMoveLeft(false);
            case RIGHT -> paddle.setMoveRight(false);
            case UP -> paddle.setMoveUp(false);
            case DOWN -> paddle.setMoveDown(false);
        }
    }

}
