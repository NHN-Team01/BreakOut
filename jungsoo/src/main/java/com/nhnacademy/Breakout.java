package com.nhnacademy;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

// 2단계까지 있는 블록깨기 게임
public class Breakout extends Application {
    // 게임 루프 컨트롤
    private AnimationTimer gameLoop;
    private boolean gameStop = false;       // 게임 일시정지 여부
    private boolean isCountingDown = false; // 카운트다운 진행 여부

    // 게임 객체 목록(패들, 공, 벽돌 등)
    private List<Shape> shapes;

    // 캔버스와 그리기 컨텍스트
    private Canvas canvas;
    private GraphicsContext gc;

    // 화면 크기
    private double screenWidth = 800, screenHeight = 600;

    // 플레이어가 조종하는 패들
    private Paddle paddle;

    // 점수, 현재 스테이지 (1~2)
    private int score = 0, currentStage = 1;

    // 공의 기본 속도
    private final double baseBallSpeed = 3.0;

    // 카운트다운 관련 (시작 시점, 3초)
    private long countdownStartTime;
    private final long COUNTDOWN_DURATION = 3_000_000_000L;

    @Override
    public void start(Stage primaryStage) {
        // 캔버스 초기화
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();

        // 스테이지(1단계) 준비 + 카운트다운 시작
        initializeShapes();
        startCountdown();

        // 메인 게임 루프
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameStop) return; // 정지 상태면 처리 안 함

                // 배경 지우기
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, screenWidth, screenHeight);

                // 바닥 표시 (빨간선)
                gc.setStroke(Color.RED);
                gc.setLineWidth(3);
                gc.strokeLine(0, screenHeight - 1, screenWidth, screenHeight - 1);

                // 카운트다운이 끝난 후에만 이동/충돌 처리
                if (!isCountingDown) {
                    if (paddle != null) {
                        paddle.move(); // 패들 이동
                    }

                    // 벽돌 제거 임시 저장
                    List<Shape> bricksToRemove = new ArrayList<>();

                    // 공 이동 & 충돌 체크
                    for (Shape shape : shapes) {
                        if (shape instanceof Ball ball) {
                            ball.move();
                            checkBallBoundaries(ball);      // 화면 경계 충돌
                            if (paddle.checkCollision(ball)) // 패들과 충돌
                                ball.bounceOffPaddle(paddle);

                            // 벽돌들과 충돌
                            for (Shape s : shapes) {
                                if (s instanceof Brick brick && brick.checkCollision(ball)) {
                                    // DoubleHitBrick은 첫 충돌 후 파괴 X
                                    if (brick instanceof DoubleHitBrick dbl && !dbl.isDestroyed()) {
                                        continue;
                                    }
                                    bricksToRemove.add(brick);
                                    score++;
                                    break;
                                }
                            }
                        }
                    }
                    shapes.removeAll(bricksToRemove);

                    // 남은 벽돌이 없으면 다음 단계 or 승리 처리
                    if (noMoreBricks()) {
                        if (currentStage == 1) {
                            currentStage = 2; // 2단계 준비
                            initializeShapes();
                            startCountdown();
                        } else {
                            // 2단계도 클리어 -> 승리
                            pauseGame();
                            showWinDialog();
                        }
                    }
                }

                // 모든 객체 그리기
                for (Shape shape : shapes) {
                    if (shape instanceof Drawable d) {
                        d.draw(gc);
                    }
                }

                // 점수 표시
                drawScore();

                // 카운트다운 중이면 남은 시간 표시
                if (isCountingDown) {
                    drawCountdown(now);
                }
            }
        };
        gameLoop.start();

        // 레이아웃 구성
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, screenWidth, screenHeight);

        // 키 입력(좌/우)
        scene.setOnKeyPressed(e -> {
            if (paddle == null) return;
            if (e.getCode() == KeyCode.LEFT) {
                paddle.setDx(-paddle.getSpeed());
            } else if (e.getCode() == KeyCode.RIGHT) {
                paddle.setDx(paddle.getSpeed());
            }
        });
        scene.setOnKeyReleased(e -> {
            if (paddle == null) return;
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                paddle.stopMoving();
            }
        });

        // 창 설정
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // 화면 경계 충돌(왼/오/위 튕김, 아래 -> 게임 오버)
    private void checkBallBoundaries(Ball ball) {
        double r = ball.getRadius();
        if (ball.getX() - r < 0) {
            ball.setX(r);
            ball.bounceX();
        } else if (ball.getX() + r > screenWidth) {
            ball.setX(screenWidth - r);
            ball.bounceX();
        }
        if (ball.getY() - r < 0) {
            ball.setY(r);
            ball.bounceY();
        } else if (ball.getY() + r > screenHeight) {
            pauseGame();
            showGameOverDialog();
        }
    }

    // 게임 오버 팝업 (다시 시작 / 종료)
    private void showGameOverDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "게임이 끝났습니다. 다시 시작하시겠습니까?",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                resetGame();
            } else {
                Platform.exit();
            }
        });
    }

    // 승리 팝업 (다시 시작 / 종료)
    private void showWinDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "승리하셨습니다! 다시 시작하시겠습니까?",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Game Won");
            alert.setHeaderText(null);

            Optional<ButtonType> res = alert.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.YES) {
                resetGame();
            } else {
                Platform.exit();
            }
        });
    }

    // 게임 재시작 (점수/스테이지 초기화 후 다시 1단계)
    private void resetGame() {
        score = 0;
        currentStage = 1;
        gameStop = false;
        shapes = new ArrayList<>();
        initializeShapes();
        startCountdown();
        gameLoop.start();
    }

    // 카운트다운 표시(화면 중앙에 숫자)
    private void drawCountdown(long now) {
        long elapsed = now - countdownStartTime;
        long remain = (COUNTDOWN_DURATION - elapsed) / 1_000_000_000;
        if (remain < 0) {
            isCountingDown = false;
        } else {
            gc.setFont(new Font(72));
            gc.setFill(Color.YELLOW);
            String text = String.valueOf(remain + 1);
            gc.fillText(text, (screenWidth - text.length() * 43) / 2, screenHeight / 2);
        }
    }

    // 점수 표시
    private void drawScore() {
        gc.setFont(new Font(24));
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(10, 10, 150, 40);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 20, 40);
    }
    // 카운트다운 시작
    private void startCountdown() {
        isCountingDown = true;
        countdownStartTime = System.nanoTime();
    }
    // 게임 일시정지
    private void pauseGame() {
        gameStop = true;
    }
    // 벽돌이 남아있는지 체크
    private boolean noMoreBricks() {
        for (Shape s : shapes) {
            if (s instanceof Brick) return false;
        }
        return true;
    }

    // 스테이지별로 (패들, 공, 벽돌) 생성
    private void initializeShapes() {
        shapes = new ArrayList<>();
        paddle = new Paddle(screenWidth / 2, screenHeight - 30, 100, 15, 5, Color.BLUE);
        shapes.add(paddle);

        Ball ball = new Ball(screenWidth / 2, screenHeight / 2, 10, 0, baseBallSpeed, Color.YELLOW);
        shapes.add(ball);

        int totalBricks = 50;
        int doubleHitCount = (currentStage == 1) ? 10 : 25;
        List<Integer> doubleHitIndices = new ArrayList<>();
        Random rand = new Random();

        while (doubleHitIndices.size() < doubleHitCount) {
            int i = rand.nextInt(totalBricks);
            if (!doubleHitIndices.contains(i)) {
                doubleHitIndices.add(i);
            }
        }
        int rows = 5, cols = 10, brickIndex = 0;
        double brickWidth = (screenWidth - (cols + 1) * 10) / cols;
        double brickHeight = 30, startX = 10, startY = 80;

        outer: for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (brickIndex >= totalBricks) break outer;
                double x = startX + col * (brickWidth + 10);
                double y = startY + row * (brickHeight + 10);
                if (doubleHitIndices.contains(brickIndex)) {
                    shapes.add(new DoubleHitBrick(x, y, brickWidth, brickHeight));
                } else {
                    shapes.add(new Brick(x, y, brickWidth, brickHeight, Color.WHITE));
                }
                brickIndex++;
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}