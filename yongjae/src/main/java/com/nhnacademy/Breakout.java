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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Breakout extends Application {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private AnimationTimer gameLoop;
    private boolean gameStop = false;
    private int[] dy = {-1 , 0 , 1};
    private int[] dx = {-1 , 0 , 1};
    int score = 0;
    Label scoreLabel = new Label();
    private Canvas canvas;
    private GraphicsContext gc;


    @Override
    public void start(Stage primaryStage) {
        gameStop = false;
        score = 0;
        moveLeft = false;
        moveRight = false;

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
        Wall[] walls = new Wall[4];
        walls[0] = new Wall(0, 0 , 800, 0);
        walls[1] = new Wall(0, 0 , 0, 600);
        walls[2] = new Wall(800, 0 , 0, 600);
        walls[3] = new Wall(0, 600 , 800, 0);
        shapes.add(walls[0]);
        shapes.add(walls[1]);
        shapes.add(walls[2]);
        shapes.add(walls[3]);

        // 벽돌 생성
        List<List<Brick>> bricks = new ArrayList<>();
        int rows = 5;
        int cols = 10;
        double brickWidth = 70;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            bricks.add(new ArrayList<>());
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                Brick temp;
                if ((row == 3 && col == 2) || (row == 3 && col == 7)) {
                    temp = new BombBrick(x, y, brickWidth, brickHeight);
                    bricks.get(row).add(temp);
                }
                else if (row == 4 && col == 4) {
                    temp = new PaddleLengthBrick(x, y, brickWidth, brickHeight);
                    bricks.get(row).add(temp);
                }
                else {
                    temp = new Brick(x, y, brickWidth, brickHeight, (int) (Math.random()*5) + 1);
                    bricks.get(row).add(temp);
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

                // Ball 업데이트 및 그리기
                ball.move();
                for(int i = 0; i < 3; i++) {
                    if (ball.isCollisionDetected(walls[i])){
                        ball.bounce(walls[i]);
                    }
                }

                // 아랫벽과 부딪치는 패배 조건
                if (ball.isCollisionDetected(walls[3])) {
                    gameStop = true;
                    showGameOverPopup(primaryStage); // 팝업 출력
                }

                // Paddle 움직임 처리
                if (moveLeft) {
                    paddle.setDx(-5);
                    paddle.move();
                }
                if (moveRight) {
                    paddle.setDx(5);
                    paddle.move();
                }

                // Paddle 경계 확인 및 그리기
                paddle.checkBounds(canvas.getWidth());

                if (paddle.isCollisionDetected(ball)) {
                    ball.setDy(-Math.abs(ball.getDy())); // 충돌 시 공의 y 방향 반전
                }

                // 벽돌 그리기 및 충돌 처리
                for (int i = 0; i < bricks.size(); i++) {
                    for (int j = 0; j < bricks.get(i).size(); j++) {
                        Brick brick = bricks.get(i).get(j);
                        if (brick.checkCollision(ball)) {
                            if (brick.isDestroyed) {
                                updateScore(brick);
                            }
                            ball.bounce(brick);
                            if (brick instanceof BombBrick) {
                                boomEffect(i, j, rows, cols, bricks);
                            }
                            else if (brick instanceof PaddleLengthBrick) {
                                widePaddleEffect(paddle);
                            }
                        }
                        if (!brick.isDestroyed()) {
                            brickCount++;
                        }
                    }
                }
                for (Shape shape : shapes) {
                    if (shape instanceof Drawable) {
                        Drawable drawable = (Drawable) shape;
                        drawable.draw(gc);
                    }
                }
                if (brickCount == 0) {
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

        scoreLabel.setFocusTraversable(false);
        canvas.setFocusTraversable(false);
    }

    private void updateScore(Brick brick) {
        score += brick.getScore();
        scoreLabel.setText("Score: " + score);
    }

    private static void widePaddleEffect(Paddle paddle) {
        paddle.setWidth(paddle.getWidth() * 2);
    }

    private void boomEffect(int i, int j, int rows, int cols, List<List<Brick>> bricks) {
        for (int k = 0; k < dy.length; k++) {
            for (int l = 0; l < dy.length; l++) {
                int ni = i + dy[k];
                int nj = j + dy[l];
                if (0 <= ni && 0<= nj && ni < rows && nj < cols) {
                    Brick b = bricks.get(ni).get(nj);
                    b.setDestroyed(true);
                    updateScore(b);
                }
            }
        }
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