package com.nhnacademy;

import com.nhnacademy.shapes.Ball;
import com.nhnacademy.shapes.Brick;
import com.nhnacademy.shapes.Paddle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Ball 생성
        Ball ball = new Ball(400, 300, 10, 3, 3, Color.RED);

        // Paddle 생성
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);

        // 벽돌 생성
        List<Brick> bricks = new ArrayList<>();
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
                bricks.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
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

                // Ball 업데이트 및 그리기
                ball.update();
                ball.checkCollision(canvas.getWidth(), canvas.getHeight());
                ball.draw(gc);

                // Paddle 움직임 처리
                if (moveLeft) {
                    paddle.moveLeft();
                }
                if (moveRight) {
                    paddle.moveRight();
                }

                // Paddle 경계 확인 및 그리기
                paddle.checkBounds(canvas.getWidth());
                paddle.draw(gc);

                if (paddle.checkCollision(ball)) {
                    ball.setDy(-ball.getDy()); // 충돌 시 공의 y 방향 반전
                }

                // 벽돌 그리기 및 충돌 처리
                for (Brick brick : bricks) {
                    if (brick.checkCollision(ball)) {
                        ball.setDy(-ball.getDy()); // 충돌 시 공의 y 방향 반전
                    }
                    brick.draw(gc);
                }
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

    public static void main(String[] args) {
        launch(args);
    }
}