package com.nhnacademy;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
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

public class Breakout extends Application implements GameEventListener {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private AnimationTimer gameLoop;
    private boolean gameStop = false;
    private List<Shape> shapes = new ArrayList<>();
    private double canvasWidth = 800;
    private double canvasHeight = 600;

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        EventManager.getInstance().addListener(this);

        // Ball 생성
        Ball ball = new Ball(400, 300, 10, 3, 3, Color.RED);
        shapes.add(ball);

        // Paddle 생성
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);
        shapes.add(paddle);

        // 벽돌 생성
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 27.5;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                shapes.add(new Brick(x + brickWidth / 2.0, y + brickHeight / 2.0, brickWidth, brickHeight, Color.BLUE));
            }
        }

        // 게임의 창 경계에 배치할 Wall 생성
        double wallThickness = 0;
        Wall leftWall = new Wall(0, 0 + canvasHeight / 2, wallThickness, canvasHeight);
        Wall rightWall = new Wall(canvasWidth, 0 + canvasHeight / 2, wallThickness, canvasHeight);
        Wall topWall = new Wall(canvasWidth / 2, 0, canvasWidth, wallThickness);
        ObstacleWall bottomWall = new ObstacleWall(canvasWidth / 2, canvasHeight, canvasWidth, wallThickness);
        shapes.add(leftWall); shapes.add(rightWall); shapes.add(topWall); shapes.add(bottomWall);

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

                // Paddle 경계 확인 및 그리기
                paddle.checkBounds(leftWall);
                paddle.checkBounds(rightWall);

                // 화면의 하단에 닿으면 게임 오버
                if(bottomWall.isCollisionDetected(ball)) {
                    bottomWall.hit();
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
            }
        };
        gameLoop.start();

        // 레이아웃 설정
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // 키보드 입력 처리
        Scene scene = new Scene(root, canvasWidth, canvasHeight);

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

    @Override
    public void onGameOver() {
        gameStop = true;
        showGameOverPopup();
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