package com.nhnacademy;

import com.nhnacademy.shapes.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Breakout extends Application {
    private boolean moveLeft;
    private boolean moveRight;
    private AnimationTimer gameLoop;
    private boolean gameStop;
    private List<Shape> shapes = new ArrayList<>();
    int score;
    private Paddle paddle;
    private Ball ball;

    private void initializeGame() {
        shapes.clear();
        score = 0;
        moveLeft = false;
        moveRight = false;
        gameStop = false;

        ball = new Ball(400, 300, 10, 3.5, 3.5, Color.RED);
        shapes.add(ball);

        paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);
        shapes.add(paddle);

        int rows = 5;
        int cols = 8;
        double brickWidth = 95;
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

        shapes.add(new Wall(0, 300, 10, 600)); // 왼쪽 벽
        shapes.add(new Wall(800, 300, 10, 600)); // 오른쪽 벽
        shapes.add(new Wall(400, 0, 800, 10)); // 상단 벽
        shapes.add(new Obstacle(400, 600, 800, 10)); // 하단 벽
    }

    @Override
    public void start(Stage primaryStage) {
        // Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Label scoreLabel = new Label("Score: "+score);
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // VBox에 점수 표시 추가
        VBox uiOverlay = new VBox();
        uiOverlay.getChildren().add(scoreLabel);
        uiOverlay.setStyle("-fx-alignment: top-center; -fx-padding: 10;");

        // StackPane에 Canvas와 UI 레이어 추가
        StackPane root = new StackPane();
        root.getChildren().addAll(canvas, uiOverlay);

        initializeGame();

        // 게임 루프
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameStop) {
                    return;
                }

                //화면 초기화
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // 입력에 따른 패들 속도 설정
                if (moveLeft) {
                    paddle.moveLeft();
                } else if (moveRight) {
                    paddle.moveRight();
                } else {
                    paddle.setDx(0);
                }

                //먼저 모든 이동 가능한 객체 이동
                for (Shape shape : shapes) {
                    if (shape instanceof Movable movable) {
                        movable.move();
                    }
                }

                //충돌 체크 및 처리
                List<Brick> bricksToRemove = new ArrayList<>();

                for (Shape shape : shapes) {
                    if (shape instanceof Wall wall) {
                        paddle.isCollisionDetected(wall);
                    }
                    if (shape != ball && ball.isCollisionDetected(shape)) {
                        if (shape instanceof Brick brick) {
                            score += 10;
                            scoreLabel.setText("Score: " + score);
                            bricksToRemove.add(brick); // 제거할 벽돌 목록에 추가
                        }
                        if (shape instanceof Blockable) {
                            showGameOverPopup();
                            return;
                        }
                        break;
                    }
                }

                //충돌한 벽돌 제거
                shapes.removeAll(bricksToRemove);

                //마지막으로 모든 객체 그리기
                for (Shape shape : shapes) {
                    if (shape instanceof Drawable drawable) {
                        drawable.draw(gc);
                    }
                }
            }
        };
        gameLoop.start();

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

    private void showGameOverPopup() {
        gameStop = true;
        gameLoop.stop();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            // 사용자 정의 메시지 및 스타일
            VBox content = new VBox(10);
            content.setAlignment(Pos.CENTER);

            Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Label scoreLabel = new Label("최종 점수: " + score);
            scoreLabel.setStyle("-fx-font-size: 18px;");

            // 버튼 생성
            ButtonType restartButton = new ButtonType("다시 시작", ButtonBar.ButtonData.OK_DONE);
            ButtonType exitButton = new ButtonType("종료", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(restartButton, exitButton);

            content.getChildren().addAll(gameOverLabel, scoreLabel);
            alert.getDialogPane().setContent(content);

            // 버튼 응답 처리
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == restartButton) {
                    // 게임 재시작 로직
                    restartGame();
                } else if (result.get() == exitButton) {
                    // 게임 완전 종료
                    Platform.exit();
                }
            }
        });
    }

    private void restartGame() {
        gameStop = false;
        initializeGame();

        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}