package com.nhnacademy;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Breakout extends Application {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private AnimationTimer gameLoop;
    private boolean gameStop = false;
    private List<Shape> shapes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ball 생성
        Ball ball = new Ball(400, 300, 10, 3, 3, Color.RED);
        shapes.add(ball);

        // Paddle 생성
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);
        shapes.add(paddle);
        shapes.add(new Ball(0, 0, 30, 0, 0,Color.WHITE));

        // 벽돌 생성
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                shapes.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
            }
        }

        // 게임 루프
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameStop) {
                    return;
                }
                // 화면 초기화
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                for(Shape shape : shapes) {
                    if(shape instanceof Bounceable) {
                        Bounceable bounceable = (Bounceable)shape;
                        bounceable.bounce(shapes);
                    }
                }
                List<Shape> objectsToRemove = new ArrayList<>();
                for(Shape shape : shapes) {
                    if(shape instanceof Breakable) {
                        Breakable breakable = (Breakable)shape;
                        if(breakable.isBroken()) {
                            objectsToRemove.add(shape);
                        }
                    }
                }
                for(Shape shape : shapes) {
                    // Movable 객체 이동 처리
                    if(shape instanceof Movable) {
                        Movable movable = (Movable)shape;
                        movable.move();
                    }
                } 
                for(Shape shape : shapes) {
                    // Drawable 객체 그리기 처리
                    if(shape instanceof Drawable) {
                        Drawable drawable = (Drawable)shape;
                        drawable.draw(gc);
                    }
                }
                
                // 순회가 끝난 후, 삭제할 객체들을 리스트에서 제거
                shapes.removeAll(objectsToRemove);

                ball.checkCollision(canvas.getWidth(), canvas.getHeight());

                // 화면의 하단에 닿으면 게임 오버
                if(ball.isAtBottom(canvas.getHeight())) {
                    gameStop = true;
                    showGameOverPopup();
                }

                // Paddle 움직임 처리
                if (moveLeft) {
                    paddle.moveLeft();
                }
                if (moveRight) {
                    paddle.moveRight();
                } 
                if (moveLeft == moveRight) { // 방향키가 동시에 눌러져 있거나 둘다 눌러져있지 않을 때 패들 속도 0으로 설정
                    paddle.setDx(0);
                }

                // Paddle 경계 확인 및 그리기
                paddle.checkBounds(canvas.getWidth());
            }
        };
        gameLoop.start();

        // 레이아웃 설정
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // 키보드 입력 처리
        Scene scene = new Scene(root, 800, 600);

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

    // 팝업을 표시하고 종료하는 메서드
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Over! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}