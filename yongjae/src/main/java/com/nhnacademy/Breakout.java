package com.nhnacademy;

import com.nhnacademy.effect.BombEffect;
import com.nhnacademy.effect.PaddleLengthEffect;
import com.nhnacademy.effect.SpeedUpEffect;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Breakout extends Application {
    private AnimationTimer gameLoop;
    private boolean gameStop = false;
    int score = 0;
    Label scoreLabel = new Label();
    private Canvas canvas;
    private GraphicsContext gc;


    @Override
    public void start(Stage primaryStage) {
        gameStop = false;
        score = 0;

        // Canvas 생성
        if (canvas == null) {
            canvas = new Canvas(800, 600);  // Canvas 객체가 없으면 새로 생성
        }
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        List<Shape> shapes = new ArrayList<>();

        // Ball 생성
        Ball ball = new Ball(400, 300, 10, 3, -3, Color.RED);
        shapes.add(ball);

        // Paddle 생성
        Paddle paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);
        shapes.add(paddle);

        // Wall 생성
        Wall[] walls = {
                new Wall(0, 0 , 800, 0),
                new Wall(0, 0 , 0, 600),
                new Wall(800, 0 , 0, 600)
        };
        shapes.addAll(Arrays.asList(walls));
        Block block = new Block(0, 600 , 800, 0);
        shapes.add(block);

        // 벽돌 생성
        int rows = 5;
        int cols = 10;
        Brick[][] bricks = new Brick[rows][cols];
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                Brick temp;
                if ((row == 3 && col == 2) || (row == 3 && col == 7)) {
                    temp = new Brick(x, y, brickWidth, brickHeight, 1, new BombEffect(rows, cols, bricks, row, col));
                    bricks[row][col] = temp;
                }
                else if (row == 4 && col == 4) {
                    temp = new Brick(x, y, brickWidth, brickHeight, 1, new PaddleLengthEffect(paddle));
                    bricks[row][col] = temp;
                }
                else if (row == 4 && col == 0) {
                    temp = new Brick(x, y, brickWidth, brickHeight, 1, new SpeedUpEffect(ball));
                    bricks[row][col] = temp;
                }
                else {
                    temp = new Brick(x, y, brickWidth, brickHeight, (int) (Math.random()*5) + 1, null);
                    bricks[row][col] = temp;
                }
                shapes.add(temp);
            }
        }

        if (gameLoop != null) {
            gameLoop.stop();
        }

        // 게임 루프
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameStop) {
                    return;
                }
                int brickCount = 0;
                // 화면 초기화
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


                // Paddle 움직임 처리
                if (paddle.getMoveLeft()) {
                    paddle.setDx(-5);
                }
                if (paddle.getMoveRight()) {
                    paddle.setDx(5);
                }

                // 벽돌 그리기 및 충돌 처리
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Brick brick = bricks[i][j];
                        if (brick == null) continue;
                        if (ball.isCollisionDetected(brick)) {
                            updateScore(brick.crash());
                            if (brick.isDestroyed()) {
                                bricks[i][j] = null;
                            }
                        }
                        brickCount++;
                    }
                }
                shapes.removeIf(shape -> shape instanceof Brick brick && brick.isDestroyed());
                for (Shape shape : shapes) {
                    if (shape instanceof Drawable drawable) {
                        drawable.draw(gc);
                    }
                    if (shape instanceof Movable movable) {
                        movable.move();
                        for (Shape other : shapes) {
                            if (movable.isCollisionDetected(other)) {
                                if (movable instanceof Bounceable bounceable) {
                                    if (other instanceof Blockable) { // 게임 종료 조건
                                        gameStop = true;
                                        showGameOverPopup(primaryStage); // 팝업 출력
                                    }
                                    bounceable.bounce(other);
                                }
                            }
                        }
                    }
                }
                if (brickCount == 0) { //게임 승리 조건
                    gameStop = true;
                    showGameClearPopup();
                }
            }
        };
        gameLoop.start();

        scoreLabel.setText("Score: " + score);
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");


        // BorderPane에 요소 배치
        BorderPane root = new BorderPane();
        root.setTop(scoreLabel);  // 상단에 점수
        root.setCenter(canvas);  // 중앙에 Canvas

        // 키보드 입력 처리
        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                paddle.setMoveLeft(true);
            } else if (event.getCode() == KeyCode.RIGHT) {
                paddle.setMoveRight(true);
            }
            else if (event.getCode() == KeyCode.SPACE) {
                if (ball.isPaused()) {
                    ball.resume();
                    paddle.resume();
                }
                else {
                    ball.pause();
                    paddle.pause();
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                paddle.setMoveLeft(false);
            } else if (event.getCode() == KeyCode.RIGHT) {
                paddle.setMoveRight(false);
            }
        });

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();

        scoreLabel.setFocusTraversable(false);
        canvas.setFocusTraversable(false);
    }

    private void updateScore(int newScore) {
        score += newScore;
        scoreLabel.setText("Score: " + score);
    }

    private void showGameOverPopup(Stage primaryStage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Over! Thank you for playing.");
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            ButtonType restart = new ButtonType("재시작");
            ButtonType exit = new ButtonType("종료");

            alert.getButtonTypes().setAll(restart, exit);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == restart) {
                    start(primaryStage);
                }
                else if (response == exit) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    private void showGameClearPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Game Clear! Thank you for playing.", ButtonType.OK);
            alert.setTitle("Game Clear");
            alert.setHeaderText(null);

            // 팝업 닫기 후 게임 종료
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit(); // 게임 종료
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}